package com.akimi808.quotescollector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.akimi808.bookmateclient.Bookmate;
import com.akimi808.bookmateclient.model.Book;
import com.akimi808.bookmateclient.model.BookmateQuote;
import com.akimi808.quotescollector.model.Author;
import com.akimi808.quotescollector.model.Quote;
import com.akimi808.quotescollector.model.Source;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by akimi808 on 29/01/2017.
 */
class SynchronizeTask extends AsyncTask<Void, Void, String> {
    private final ProgressDialog dialog;
    private QuoteManager quoteManager;

    public SynchronizeTask(Activity activity, QuoteManager quoteManager) {

        this.quoteManager = quoteManager;
        dialog = new ProgressDialog(activity);
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
        boolean wasStored = false;
        Set<String> downloadedBooks = new HashSet<>();
        Map<String, Source> storedSources = new HashMap<>();
        Map<String, Author> storedAuthors = new HashMap<>();
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
                        downloadedBooks.add(documentUuid);
                        storedSources.put(documentUuid, storeBookIfNotExist(book));
                        storedAuthors.put(documentUuid, storeAuthorIfNotExist(book));
                    } else {
                        Log.d("Sync", "Book with uuid [" + documentUuid + "] is already downloaded");
                    }
                    wasStored = wasStored || storeQuoteIfNotExist(quote, storedSources.get(documentUuid), storedAuthors.get(documentUuid));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            page = page + 1;
            quoteCount = quoteCount + quoteListSize;
        } while (quoteListSize == 10);
        Log.d("Sync", "Total Quotes downloaded: " + quoteCount);
        if (wasStored) {
            quoteManager.dataChanged();
        }
    }

    private Author storeAuthorIfNotExist(Book book) {
        if (!quoteManager.isAuthorStored(book.getAuthors())) {
            Author author = new Author(null, book.getAuthors());
            quoteManager.storeAuthor(author);
            return author;
        }
        return quoteManager.qetAuthor(book.getAuthors());
    }

    private Source storeBookIfNotExist(Book book) {
        if (!quoteManager.isSourceStored(book.getUuid(), "Bookmate")) {
            Source source = new Source(null, book.getTitle(), "book", "Bookmate", book.getUuid());
            quoteManager.storeSource(source);
            return source;
        }
        return quoteManager.qetSource(book.getUuid(), "Bookmate");
    }

    private boolean storeQuoteIfNotExist(BookmateQuote bookmateQuote, Source source, Author author) {
        if (!quoteManager.isQuoteStored(bookmateQuote.getUuid(), "Bookmate")) {
            Quote quote = new Quote(null, bookmateQuote.getContent(), bookmateQuote.getUuid(), author, source, "Bookmate");
            quoteManager.storeQuote(quote);
            return true;
        }
        return false;
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
