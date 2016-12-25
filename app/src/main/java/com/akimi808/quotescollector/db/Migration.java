package com.akimi808.quotescollector.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by akimi808 on 25/12/2016.
 */
//классы, реализующие интерфейс, будут выполнять работу по миграции данных
public interface Migration {
    void doUpgrade(SQLiteDatabase db);
}
