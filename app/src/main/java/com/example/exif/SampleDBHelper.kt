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
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            database?.execSQL("alter table Album add column deleteFlag integer default 0")
        }
    }
}