package com.akimi808.quotescollector.db;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.akimi808.quotescollector.QuoteManager;
import com.akimi808.quotescollector.model.Quote;

/**
 * Created by akimi808 on 13/12/2016.
 */

public class DbQuoteManager implements QuoteManager {
    private QuoteDbOpenHelper helper;

    public DbQuoteManager(QuoteDbOpenHelper helper) {
        this.helper = helper;
    }

    @Override
    public int getQuoteCount() {
        return (int) DatabaseUtils.queryNumEntries(helper.getReadableDatabase(), "quotes");
    }

    @Override
    public Quote getQuoteByIndex(int index) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = new String[] {
                "text",
                "author",
                "source",
        };
        String limit = (index > 0 ? index + ", " : "") + "1";
        String orderBy = "id asc";
        Cursor cursor = null;
        try {
            cursor = db.query("quotes", columns, null, null, null, null, orderBy, limit);
            if (cursor.moveToFirst()) {
                String text = cursor.getString(cursor.getColumnIndex("text"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String source = cursor.getString(cursor.getColumnIndex("source"));
                return new Quote(text, author, source);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        throw new RuntimeException();
    }
}
