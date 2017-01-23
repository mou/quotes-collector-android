package com.akimi808.quotescollector;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.akimi808.bookmateclient.Bookmate;
import com.akimi808.bookmateclient.model.Book;
import com.akimi808.bookmateclient.model.BookmateQuote;
import com.akimi808.quotescollector.db.DbQuoteManager;
import com.akimi808.quotescollector.db.QuoteDbOpenHelper;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuotesListActivity extends AppCompatActivity {

    private QuoteManager quoteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_list);

        RecyclerView rv = (RecyclerView) findViewById(R.id.quotes_recycler_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);
        quoteManager = new DbQuoteManager(new QuoteDbOpenHelper(this, getResources()));
        rv.setAdapter(new QuoteAdapter(quoteManager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //метод, кот. возвращает объект, ответственный за размещение меню в Activity, у него вызываем метод inflate
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_sync:
                new SynchronizeTask(quoteManager).execute((Void[]) null);
                return true;
        }
        return false;
    }

    class SynchronizeTask extends AsyncTask<Void, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(QuotesListActivity.this);
        private QuoteManager quoteManager;

        public SynchronizeTask(QuoteManager quoteManager) {

            this.quoteManager = quoteManager;
        }

        @Override
        protected String doInBackground(Void... voids) {
            Bookmate bookmate = new Retrofit.Builder()
                    .baseUrl("http://bookmate.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Bookmate.class);

            downloadQuotes(bookmate);
            return "";
        }

        private void downloadQuotes(Bookmate bookmate) {
            int quoteListSize = 0;
            int page = 1;
            int quoteCount = 0;
            Set<String> downloadedBooks = new HashSet<>();
            do {
                //возвращает promise, что внутри объекта появится список с цитатами, обещание сделать работу
                Call<List<BookmateQuote>> quotes = bookmate.quotes("vkontakte085", false, page, 10);
                try {
                    //результат выполнения работы, ответ сервера, там м.б. ошибка
                    Response<List<BookmateQuote>> response = quotes.execute();
                    List<BookmateQuote> quoteList = response.body();
                    quoteListSize = quoteList.size();
                    for (BookmateQuote quote : quoteList) {
                        Log.d("Sync", quote.toString());
                        String documentUuid = quote.getDocumentUuid();
                        if (!downloadedBooks.contains(documentUuid)) {
                            Book book = downloadBook(bookmate, documentUuid);
                            storeIfNotExist(book, quoteManager);
                            downloadedBooks.add(documentUuid);
                        } else {
                            Log.d("Sync", "Book with uuid [" + documentUuid + "] is already downloaded");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                page = page + 1;
                quoteCount = quoteCount + quoteListSize;
            } while (quoteListSize == 10);
            Log.d("Sync", "Total Quotes downloaded: " + quoteCount);
        }

        private void storeIfNotExist(Book book, QuoteManager quoteManager) {
           // quoteManager.qetSourceId
        }

        private Book downloadBook(Bookmate bookmate, String documentUuid) {
            Call<Book> bookCall = bookmate.book(documentUuid);
            try {
                Response<Book> response = bookCall.execute();
                Book book = response.body();
                Log.d("Sync", book.toString());
                return book;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Synchronize with Bookmate...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }


}
