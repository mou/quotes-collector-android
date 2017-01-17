package com.akimi808.quotescollector.db.versions;

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
        db.execSQL("CREATE TABLE sourses (\n" +
                "        id INTEGER PRIMARY KEY,\n" +
                "        name TEXT);\n");
        //добавляем столбец source_id в таблицу quotes
        db.execSQL("ALTER TABLE quotes ADD COLUMN source_id INTEGER;");
    }

    private void extractSourcesFromQuotesTable(SQLiteDatabase db) {
    }

    private void deleteSourceColumn(SQLiteDatabase db) {
    }

}
