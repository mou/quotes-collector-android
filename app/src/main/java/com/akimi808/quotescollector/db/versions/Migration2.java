package com.akimi808.quotescollector.db.versions;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.akimi808.quotescollector.R;
import com.akimi808.quotescollector.db.Migration;

/**
 * Created by akimi808 on 25/12/2016.
 */

public class Migration2 implements Migration {
    private Resources resources;

    public Migration2(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void doUpgrade(SQLiteDatabase db) {
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
}
