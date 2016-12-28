package com.akimi808.quotescollector.db;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.akimi808.quotescollector.QuoteManager;
import com.akimi808.quotescollector.model.Author;
import com.akimi808.quotescollector.model.Quote;
import com.akimi808.quotescollector.model.Source;

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
        Log.d("DB", "Requested quote with index [" + index + "]");
        SQLiteDatabase db = helper.getReadableDatabase();
        String limit = (index > 0 ? index + ", " : "") + "1";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT " +
                    "q.id quote_id, " +
                    "q.text quote_text, " +
                    "q.source quote_source, " +
                    "a.id author_id, " +
                    "a.name author_name " +
                    "FROM quotes q " +
                    "JOIN authors a ON q.author_id = a.id " +
                    "ORDER BY q.id asc " +
                    "LIMIT " + limit, null);
            if (cursor.moveToFirst()) {
                String text = cursor.getString(cursor.getColumnIndex("quote_text"));
                Long authorId = cursor.getLong(cursor.getColumnIndex("author_id"));
                String authorName = cursor.getString(cursor.getColumnIndex("author_name"));
                String source = cursor.getString(cursor.getColumnIndex("quote_source"));
                Quote quote = new Quote(text, new Author(authorId, authorName), new Source(null, source, "book", " "));
                Log.d("DB", "For index [" + index + "] from DB retreived quote " + quote.toString());
                return quote;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        throw new RuntimeException();
    }
}
