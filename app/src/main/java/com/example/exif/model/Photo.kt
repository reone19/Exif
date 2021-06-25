package com.example.exif.model.exif

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//写真テーブル

@Entity(tableName = "photo")
data class Photo (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "photo_name") val photoName: String,
    @ColumnInfo(name = "delete_date") val deleteDate: Long?
)