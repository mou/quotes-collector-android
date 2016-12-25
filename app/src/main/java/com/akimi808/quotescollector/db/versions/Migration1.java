package com.akimi808.quotescollector.db.versions;

import android.database.sqlite.SQLiteDatabase;

import com.akimi808.quotescollector.db.Migration;

/**
 * Created by akimi808 on 25/12/2016.
 */
public class Migration1 implements Migration {
    @Override
    public void doUpgrade(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE quotes (\n" +
                "        id INTEGER PRIMARY KEY,\n" +
                "        text TEXT,\n" +
                "        author TEXT,\n" +
                "        source TEXT)");
    }
}
