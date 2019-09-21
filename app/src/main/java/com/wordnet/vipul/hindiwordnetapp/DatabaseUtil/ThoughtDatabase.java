package com.wordnet.vipul.hindiwordnetapp.DatabaseUtil;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = ThoughtDatabase.NAME, version = ThoughtDatabase.VERSION)
public class ThoughtDatabase {

    public static final String NAME = "thoughts";

    public static final int VERSION = 1;
}
