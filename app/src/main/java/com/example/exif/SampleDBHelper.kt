package com.example.exif

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

public class SampleDBHelper(
    context: Context,
    databaseName: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, databaseName, factory, version) {

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("create table if not exists Album (id text primary key, text_id text, subtext_id text, photo_num integer, name text, num integer)");
        database?.execSQL("create table if not exists Photo (id text primary key, path text, name text)");
        database?.execSQL("create table if not exists Album_Photo (photo_id text, album_id text, FOREIGN KEY(photo_id) REFERENCES Photo(id), FOREIGN KEY(album_id) REFERENCES Album(id))");
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            database?.execSQL("alter table Album add column deleteFlag integer default 0")
            database?.execSQL("alter table Photo add column deleteFlag integer default 0")
            database?.execSQL("alter table Album_Photo add column deleteFlag integer default 0")
        }
    }
}