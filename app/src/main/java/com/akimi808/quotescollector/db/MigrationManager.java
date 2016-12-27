package com.akimi808.quotescollector.db;

import android.content.res.Resources;

import com.akimi808.quotescollector.db.versions.Migration1;
import com.akimi808.quotescollector.db.versions.Migration2;

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
    }
    //дает миграцию до нужной версии
    public Migration getMigration(int ver) {
        //не забыть про нумерацию элементов в списке (zero-based index)
        return migrations.get(ver-1);
    }
}
