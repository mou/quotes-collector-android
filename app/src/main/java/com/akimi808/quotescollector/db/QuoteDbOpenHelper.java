package com.akimi808.quotescollector.db;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by akimi808 on 13/12/2016.
 */
//Помогает открыть, создать, изменить БД, переходить между версиями
public class QuoteDbOpenHelper extends SQLiteOpenHelper {
    private static final String DATABEASE_NAME = "quotes.db";
    private static final int DATABASE_VER = 2;
    private final MigrationManager migrationManager;

    public QuoteDbOpenHelper(Context context, Resources resources) {
        super(context, DATABEASE_NAME, null, DATABASE_VER);
        migrationManager = new MigrationManager(resources);
    }

    @Override
    //вызовется один раз, когда класс создаст файл БД
    public void onCreate(SQLiteDatabase db) {
        //метод исполняет указанный SQL-запрос, указанный в переданной строке
        for (int i = 1; i <= DATABASE_VER; i++) {
            migrationManager.getMigration(i).doUpgrade(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int current) {
        for (int ver = old + 1; ver <= current; ver++) {
            migrationManager.getMigration(ver).doUpgrade(db);
        }
    }
}
