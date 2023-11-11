package com.mvaldiviezoutp.cxmara.Almacenar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

public class SwipeToDeleteCallback extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ImageDatabase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_IMAGES = "images";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE_URI = "image_uri";

    private static final String CREATE_TABLE_IMAGES =
            "CREATE TABLE " + TABLE_IMAGES + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_IMAGE_URI + " TEXT" +
                    ")";

    public SwipeToDeleteCallback(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Puedes manejar la actualización de la base de datos si es necesario
    }

    public void saveImageUri(Uri uri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_URI, uri.toString());
        db.insert(TABLE_IMAGES, null, values);
        db.close();
    }

    public ArrayList<Uri> getSavedImageUris() {
        ArrayList<Uri> uris = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_IMAGES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String uriString = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI));
                uris.add(Uri.parse(uriString));
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return uris;
    }
}



