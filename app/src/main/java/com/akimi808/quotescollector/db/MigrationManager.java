package com.akimi808.quotescollector.db;

import android.content.res.Resources;

import com.akimi808.quotescollector.db.versions.Migration1;
import com.akimi808.quotescollector.db.versions.Migration2;

import java.util.ArrayList;

/**
 * Created by akimi808 on 25/12/2016.
 */

public class MigrationManager {

    private final ArrayList<Migration> migrations;

    public MigrationManager(Resources resources) {
        migrations = new ArrayList<>();
        migrations.add(new Migration1());
        migrations.add(new Migration2(resources));
    }

    public Migration getMigration(int ver) {
        return null;
    }
}
