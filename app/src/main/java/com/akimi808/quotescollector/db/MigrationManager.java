package com.akimi808.quotescollector.db;

import android.content.res.Resources;

import com.akimi808.quotescollector.db.versions.Migration1;
import com.akimi808.quotescollector.db.versions.Migration2;
import com.akimi808.quotescollector.db.versions.Migration3;
import com.akimi808.quotescollector.db.versions.Migration4;
import com.akimi808.quotescollector.db.versions.Migration5;
import com.akimi808.quotescollector.db.versions.Migration6;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akimi808 on 25/12/2016.
 */

public class MigrationManager {

    private final List<Migration> migrations;

    public MigrationManager(Resources resources) {
        migrations = new ArrayList<>();
        migrations.add(new Migration1());
        migrations.add(new Migration2(resources));
        migrations.add(new Migration3());
        migrations.add(new Migration4());
        migrations.add(new Migration5());
        migrations.add(new Migration6());
    }
    //дает миграцию до нужной версии
    public Migration getMigration(int ver) {
        //не забыть про нумерацию элементов в списке (zero-based index)
        return migrations.get(ver-1);
    }
}
