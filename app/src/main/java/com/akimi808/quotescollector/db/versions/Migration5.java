package com.akimi808.quotescollector.db.versions;

import android.database.sqlite.SQLiteDatabase;

import com.akimi808.quotescollector.db.Migration;

/**
 * Created by akimi808 on 23/01/2017.
 */

public class Migration5 implements Migration {
    @Override
    public void doUpgrade(SQLiteDatabase db) {
        addColunms(db);
    }

    private void addColunms(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE quotes ADD COLUMN external_id TEXT;");
        db.execSQL("ALTER TABLE quotes ADD COLUMN application TEXT;");
    }
}
