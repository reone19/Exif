package com.example.exif

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

public class SampleDBHelper(
    context: MainActivity,
    databaseName: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, databaseName, factory, version) {

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("create table if not exists Album (id text primary key, text_id text, subtext_id text, photo_num integer, name text, num integer)");
        database?.execSQL("create table if not exists Photo (id text primary key, path text, name text, sentence1 text, sentence2 text, sentence3 text)");
        database?.execSQL("create table if not exists Album_Photo (photo_id text, album_id text, FOREIGN KEY(photo_id) REFERENCES Photo(id), FOREIGN KEY(album_id) REFERENCES Album(id))");
        database?.execSQL("create table if not exists Meta (photo_id text primary key, imageName text, imageLength text, imageWidth text, bitsPerSample text, compression text, imageDescription text, imageOrientation text, maker text, model text, stripOffsets text, gpsVersionID text, gpsLatitude text, gpsLongitude text, dateTimeOriginal text, changeDateAndTime text, FOREIGN KEY(photo_id) REFERENCES Photo(id))");
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            database?.execSQL("alter table Album add column deleteFlag integer default 0")
            database?.execSQL("alter table Photo add column deleteFlag integer default 0")
            database?.execSQL("alter table Album_Photo add column deleteFlag integer default 0")
            database?.execSQL("alter table Meta add column deleteFlag integer default 0")
        }
    }
}