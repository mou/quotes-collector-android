package com.akimi808.quotescollector;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by akimi808 on 19/03/2017.
 */

class ClearDbTask extends AsyncTask<Void, Void, String> {
    private final Context context;
    private final QuoteManager quoteManager;

    public ClearDbTask(Context context, QuoteManager quoteManager) {
        this.context = context;
        this.quoteManager = quoteManager;
    }

    @Override
    protected String doInBackground(Void... voids) {
        quoteManager.deleteQuotes();
        return null;
    }


}
