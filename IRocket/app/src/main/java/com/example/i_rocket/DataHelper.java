package com.example.i_rocket;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "expedition.db";

    public DataHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS expedition (" +
                "id INTEGER PRIMARY KEY, " +
                "url TEXT, " +
                "name TEXT, " +
                "`start` TEXT, " +
                "`end` TEXT, " +
                "spacestation TEXT," +
                "image_url TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS expedition");
        onCreate(db);
    }
}
