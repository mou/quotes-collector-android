package com.akimi808.quotescollector.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.akimi808.quotescollector.R;

/**
 * Created by akimi808 on 13/12/2016.
 */
//Помогает открыть, создать, изменить БД, переходить между версиями
public class QuoteDbOpenHelper extends SQLiteOpenHelper {
    private static final String DATABEASE_NAME = "quotes.db";
    private static final int DATABASE_VER = 2;
    private Resources resources;

    public QuoteDbOpenHelper(Context context, Resources resources) {
        super(context, DATABEASE_NAME, null, DATABASE_VER);
        this.resources = resources;
    }
    @Override
    //вызовется один раз, когда класс создаст файл БД
    public void onCreate(SQLiteDatabase db) {
        //метод исполняет указанный SQL-запрос, указанный в переданной строке
        db.execSQL("CREATE TABLE quotes (\n" +
                "        id INTEGER PRIMARY KEY,\n" +
                "        text TEXT,\n" +
                "        author TEXT,\n" +
                "        source TEXT)");
    }

    private void populate(SQLiteDatabase db) {
        String[] quoteTexts = resources.getStringArray(R.array.quote_texts);
        int arrayLength =  quoteTexts.length;
        for (int i = 0; i < arrayLength; i++) {
            String text = resources.getStringArray(R.array.quote_texts)[i];
            String author = resources.getStringArray(R.array.quote_authors)[i];
            String source = resources.getStringArray(R.array.quote_sources)[i];
            ContentValues values = new ContentValues();
            values.put("author", author);
            values.put("source", source);
            values.put("text", text);
            db.insert("quotes", null, values);
        }
    }

    @Override
    //
    public void onUpgrade(SQLiteDatabase db, int old, int current) {
        if (current == 2) {
            populate(db);
        }
    }
}
