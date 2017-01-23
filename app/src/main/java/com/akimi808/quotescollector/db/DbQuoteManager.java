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
                    "q.external_id external_id, " +
                    "q.application quote_application, " +
                    "s.id source_id, " +
                    "s.title source_title, " +
                    "s.type source_type, " +
                    "s.application source_application, " +
                    "a.id author_id, " +
                    "a.name author_name " +
                    "FROM quotes q " +
                    "JOIN authors a ON q.author_id = a.id " +
                    "JOIN sources s ON q.source_id = s.id " +
                    "ORDER BY q.id asc " +
                    "LIMIT " + limit, null);
            if (cursor.moveToFirst()) {
                Long id = cursor.getLong(cursor.getColumnIndex("quote_id"));
                String text = cursor.getString(cursor.getColumnIndex("quote_text"));
                Long authorId = cursor.getLong(cursor.getColumnIndex("author_id"));
                String authorName = cursor.getString(cursor.getColumnIndex("author_name"));
                Long sourceId = cursor.getLong(cursor.getColumnIndex("source_id"));
                String sourceTitle = cursor.getString(cursor.getColumnIndex("source_title"));
                String sourceType = cursor.getString(cursor.getColumnIndex("source_type"));
                String sourceApp = cursor.getString(cursor.getColumnIndex("source_application"));
                Author author = new Author(authorId, authorName);
                Source book = new Source(sourceId, sourceTitle, sourceType, sourceApp);
                String externalId = cursor.getString(cursor.getColumnIndex("external_id"));
                String application = cursor.getString(cursor.getColumnIndex("quote_application"));
                Quote quote = new Quote(id, text, externalId, author, book, application);
                Log.d("DB", "For index [" + index + "] from DB retrieved quote " + quote.toString());
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
