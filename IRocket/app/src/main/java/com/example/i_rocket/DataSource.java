package com.example.i_rocket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;

public class DataSource {
    public static ArrayList<Expedition> expeditions_off = new ArrayList<>();
    private static DataHelper dbHelper;

    public static void addExpedition(Context context, int id, String url, String name, String start, String end, String spacestation, String image_url) {
        dbHelper = new DataHelper(context);
        Log.d("DataSource", "addExpedition: Adding expedition");
        SQLiteDatabase db = null;
        long newRowId = -1;

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("url", url);
            values.put("name", name);
            values.put("start", start);
            values.put("end", end);
            values.put("spacestation", spacestation);
            values.put("image_url", image_url);
            newRowId = db.insert("expedition", null, values);
        } catch (Exception e) {
            Log.e("DataSource", "Error adding expedition", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }
    public static void removeExpedition(Context context, int id) {
        dbHelper = new DataHelper(context);
        SQLiteDatabase db = null;
        int rowsAffected = -1;
        try {
            db = dbHelper.getWritableDatabase();
            rowsAffected = db.delete("expedition", "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("DataSource", "Error removing expedition", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }

    public static void getData(Context context) {
        expeditions_off.clear();
        dbHelper = new DataHelper(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query("expedition", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String url = cursor.getString(1);
                    String name = cursor.getString(2);
                    String start = cursor.getString(3);
                    String end = cursor.getString(4);
                    String spacestation = cursor.getString(5);
                    String image = cursor.getString(6);
                    expeditions_off.add(new Expedition(id, name, url, start, end, spacestation, image));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DataSource", "Error getting data", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
}
