package com.example.exif.model.exif

import androidx.room.Database
import androidx.room.RoomDatabase

//データベースの定義

//データベースに含めるテーブルのエンティティ
@Database(entities = [
    Album::class,
    Photo::class,
    AlbumPhoto::class,
    Meta::class,
    MetaPhoto::class],
    version = 1)

abstract class ExifDatabase: RoomDatabase() {
    abstract fun exifDAO(): ExifDao
}