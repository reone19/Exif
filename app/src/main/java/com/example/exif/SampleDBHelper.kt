package com.example.exif

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SampleDBHelper(
    context: Context,
    databaseName: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, databaseName, factory, version) {


    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("create table if not exists Album (id text primary key, text_id text, subtext_id text, photo_num integer, name text, num integer)");
        database?.execSQL("create table if not exists Photo (id text primary key, path text, name text, sentence1 text, sentence2 text, sentence3 text, size long, ocr text)");
        database?.execSQL("create table if not exists Album_Photo (photo_id text, album_id text, FOREIGN KEY(photo_id) REFERENCES Photo(id), FOREIGN KEY(album_id) REFERENCES Album(id))");
        database?.execSQL("create table if not exists Meta (photo_id text primary key, image_name text, image_length text, image_width text, y_resolution text, x_resolution text, bits_per_sample text, compression text, image_orientation text, image_description text, artist text, maker text, model text, aperture text, exposure_time text, iso_speed text, exposure_bias text, f_number text, shutter_speed text, focal_length text, metering_mode text, flash text, strip_offsets text, gps_version_id text, gps_latitude text, gps_longitude text, gps_altitude text, date_time_original text, change_date_and_time text, FOREIGN KEY(photo_id) REFERENCES Photo(id))");
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