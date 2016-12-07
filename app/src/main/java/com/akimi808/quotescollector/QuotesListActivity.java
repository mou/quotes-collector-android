package com.akimi808.quotescollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        QuoteManager quoteManager = new ResourceQuoteManager(getResources());
        rv.setAdapter(new QuoteAdapter(quoteManager));
    }

}
