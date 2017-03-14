package com.akimi808.quotescollector.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.akimi808.quotescollector.QuoteAdapter;
import com.akimi808.quotescollector.QuoteManager;
import com.akimi808.quotescollector.model.Author;
import com.akimi808.quotescollector.model.Quote;
import com.akimi808.quotescollector.model.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akimi808 on 13/12/2016.
 */

public class DbQuoteManager implements QuoteManager {
    private QuoteDbOpenHelper helper;
    private List<DataChangedListener> listeners = new ArrayList<>();

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
                    "s.external_id source_external_id, " +
                    "a.id author_id, " +
                    "a.name author_name " +
                    "FROM quotes q " +
                    "JOIN authors a ON q.author_id = a.id " +
                    "JOIN sources s ON q.source_id = s.id " +
                    "ORDER BY q.id asc " +
                    "LIMIT " + limit, null);
            if (cursor.moveToFirst()) {
                String text = cursor.getString(cursor.getColumnIndex("quote_text"));
                Long authorId = cursor.getLong(cursor.getColumnIndex("author_id"));
                String authorName = cursor.getString(cursor.getColumnIndex("author_name"));
                Author author = new Author(authorId, authorName);

                Long sourceId = cursor.getLong(cursor.getColumnIndex("source_id"));
                String sourceExternalId = cursor.getString(cursor.getColumnIndex("source_external_id"));
                String sourceTitle = cursor.getString(cursor.getColumnIndex("source_title"));
                String sourceType = cursor.getString(cursor.getColumnIndex("source_type"));
                String sourceApp = cursor.getString(cursor.getColumnIndex("source_application"));
                Source book = new Source(sourceId, sourceTitle, sourceType, sourceApp, sourceExternalId);

                Long id = cursor.getLong(cursor.getColumnIndex("quote_id"));
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

    @Override
    public boolean isSourceStored(String externalId, String application) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        String[] args = new String[] {externalId, application};
        String[] columns = new String[] {"id"};
        try {
            cursor = db.query("sources", columns, "external_id = ? AND application = ?", args, null, null, null);
            return cursor.moveToNext();
        } finally {
            if (cursor != null) { cursor.close();}
        }
    }

    @Override
    public void storeSource(Source source) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", source.getTitle());
        values.put("type", source.getType());
        values.put("application", source.getApplication());
        values.put("external_id", source.getExternalId());
        db.insert("sources", null, values);

    }

    @Override
    public boolean isAuthorStored(String authors) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        String[] args = new String[] {authors};
        String[] columns = new String[] {"id"};
        try {
            cursor = db.query("authors", columns, "name = ?", args, null, null, null);
            return cursor.moveToNext();
        } finally {
            if (cursor != null) { cursor.close();}
        }
    }

    @Override
    public void storeAuthor(Author author) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", author.getName());
        db.insert("authors", null, values);

    }

    @Override
    public boolean isQuoteStored(String externalId, String application) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        String[] args = new String[] {externalId, application};
        String[] columns = new String[] {"id"};
        try {
            cursor = db.query("quotes", columns, "external_id = ? AND application = ?", args, null, null, null);
            return cursor.moveToNext();
        } finally {
            if (cursor != null) { cursor.close();}
        }
    }

    @Override
    public Author qetAuthor(String authors) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        String[] args = new String[] {authors};
        String[] columns = new String[] {"id"};
        try {
            cursor = db.query("authors", columns, "name = ?", args, null, null, null);
            cursor.moveToNext();
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            return new Author(id, authors);
        } finally {
            if (cursor != null) { cursor.close();}
        }

    }

    @Override
    public Source qetSource(String externalId, String application) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        String[] args = new String[] {externalId, application};
        String[] columns = new String[] {"id", "title", "type"};
        try {
            cursor = db.query("sources", columns, "external_id = ? AND application = ?", args, null, null, null);
            cursor.moveToNext();
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            return new Source(id, title, type, application, externalId);
        } finally {
            if (cursor != null) { cursor.close();}
        }


    }

    @Override
    public void storeQuote(Quote quote) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", quote.getId());
        values.put("text", quote.getText());
        values.put("external_id", quote.getExternalId());
        values.put("application", quote.getApplication());
        values.put("source_id", quote.getSource().getId());
        values.put("author_id", quote.getAuthor().getId());
        db.insert("quotes", null, values);
        dataChanged(); //говорим, что событие наступило
    }

    @Override
    public void dataChanged() {
        for (DataChangedListener listener : listeners) {
            listener.onDataChanged();
        }
    }

    @Override
    public void registerForDataChanged(DataChangedListener listener) {
        listeners.add(listener);
    }
}
