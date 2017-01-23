package com.akimi808.quotescollector.db.versions;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akimi808.quotescollector.db.Migration;

/**
 * Created by akimi808 on 17/01/2017.
 */

public class Migration4 implements Migration {
    @Override
    public void doUpgrade(SQLiteDatabase db) {
        createSourceTable(db);
        extractSourcesFromQuotesTable(db);
        deleteSourceColumn(db);

    }


    private void createSourceTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sources (\n" +
                "        id INTEGER PRIMARY KEY,\n" +
                "        title TEXT, \n" +
                "        type TEXT, \n" +
                "        application TEXT);\n");
        //добавляем столбец source_id в таблицу quotes
        db.execSQL("ALTER TABLE quotes ADD COLUMN source_id INTEGER;");
    }

    private void extractSourcesFromQuotesTable(SQLiteDatabase db) {

        String[] columns = new String[] { "id", "source" };
        Cursor quotes = db.query("quotes", columns, null, null, null, null, null); //курсор сейчас перед первой записью
        while (quotes.moveToNext()) {
            String sourceName = quotes.getString(quotes.getColumnIndex("source"));
            Long quoteId = quotes.getLong(quotes.getColumnIndex("id"));

            String[] sourceColumns = new String[] {"id"};
            String[] args = new String[] {sourceName};
            Cursor sources = db.query("sources", sourceColumns, "title = ?", args, null, null, null);
            Long sourceId;
            if (sources.moveToNext()) {
                sourceId = sources.getLong(sources.getColumnIndex("id"));
            } else {
                ContentValues insertValues = new ContentValues();
                insertValues.put("title", sourceName);
                sourceId = db.insert("sources", null, insertValues);
            }
            ContentValues values = new ContentValues();
            values.put("source_id", sourceId);
            db.update("quotes", values, "id = " + quoteId, null);
            sources.close();
        }
        quotes.close();
    }

    private void deleteSourceColumn(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sources_mod (\n" +
                "        id INTEGER PRIMARY KEY,\n" +
                "        text TEXT,\n" +
                "        author_id INTEGER,\n" +
                "        source_id INTEGER )");
        db.execSQL("INSERT INTO sources_mod (id, text, author_id, source_id) SELECT id, text, author_id, source_id FROM quotes;");
        db.execSQL("DROP TABLE quotes;");
        db.execSQL("ALTER TABLE sources_mod RENAME TO quotes;");
    }

}
