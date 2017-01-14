package com.akimi808.quotescollector;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.akimi808.quotescollector.db.DbQuoteManager;
import com.akimi808.quotescollector.db.QuoteDbOpenHelper;

public class QuotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_list);

        RecyclerView rv = (RecyclerView) findViewById(R.id.quotes_recycler_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(llm);
        QuoteManager quoteManager = new DbQuoteManager(new QuoteDbOpenHelper(this, getResources()));
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
                new SynchronizeTask().execute((Void[]) null);
                return true;
        }
        return false;
    }

    class SynchronizeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            return "";
        }
    }


}
