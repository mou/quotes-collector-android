package com.akimi808.quotescollector.db.versions;

import android.content.ContentValues;
import android.database.Cursor;
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
    /*
        //Для каждой записи в таблице Quote
        //query - метод, выполняющий SQL-запрос на выбор записей из базы данных, возвращает Cursor
        //создание массива с содержимым
            //берем значение поля author
            //ищем в таблице author запись, у которой поле name == Quote.Author
                //если запись найдена:
                    //записываем в таблицу Quote в поле author_id значение id у найденной записи
                //если нет:
                    //создаем запись в таблице authors, и указываем значение поля name значение из Quote.Author
                    //берем значение id у созданной записи и помещаем его в поле Quote.Author_id
     */
    private void extractAuthorsFromQuotesTable(SQLiteDatabase db) {

        String[] columns = new String[] { "id", "author" };
        Cursor quotes = db.query("quotes", columns, null, null, null, null, null); //курсор сейчас перед первой записью
        while (quotes.moveToNext()) {
            String authorName = quotes.getString(quotes.getColumnIndex("author"));
            Long quoteId = quotes.getLong(quotes.getColumnIndex("id"));

            String[] authorColumns = new String[] {"id"};
            String[] args = new String[] {authorName};
            Cursor authors = db.query("authors", authorColumns, "name = ?", args, null, null, null);
            Long authorId;
            if (authors.moveToNext()) {
                authorId = authors.getLong(authors.getColumnIndex("id"));
            } else {
                ContentValues insertValues = new ContentValues();
                insertValues.put("name", authorName);
                authorId = db.insert("authors", null, insertValues);
            }
            ContentValues values = new ContentValues();
            values.put("author_id", authorId);
            db.update("quotes", values, "id = " + quoteId, null);


        }
    }

    private void deleteAuthorColumn(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE quotes DROP COLUMN author;");
    }


}
