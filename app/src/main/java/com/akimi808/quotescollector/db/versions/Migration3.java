package com.akimi808.quotescollector.db.versions;

import android.database.sqlite.SQLiteDatabase;

import com.akimi808.quotescollector.db.Migration;

/**
 * Created by akimi808 on 25/12/2016.
 */

public class Migration3 implements Migration {
    @Override
    public void doUpgrade(SQLiteDatabase db) {
        createAuthorsTable(db);
        extractAuthorsFromQuotesTable(db);
        deleteAuthorColumn(db);
    }

    private void createAuthorsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE authors (\n" +
                "        id INTEGER PRIMARY KEY,\n" +
                "        name TEXT);\n");
        //добавляем столбец author_id в таблицу quotes
        db.execSQL("ALTER TABLE quotes ADD COLUMN author_id INTEGER;");
    }

    private void extractAuthorsFromQuotesTable(SQLiteDatabase db) {
        //Для каждой записи в таблице Quote
            //берем значение поля author
            //ищем в таблице author запись, у которой поле name == Quote.Author
            //если запись найдена:
                //записываем в таблицу Quote в поле author_id значение id у найденной записи
            //если нет:
                //создаем запись в таблице Author, и указываем значение поля name значение из Quote.Author
                //берем значение id у созданной записи и помещаем его в поле Quote.Author_id
        new
    }

    private void deleteAuthorColumn(SQLiteDatabase db) {

    }


}
