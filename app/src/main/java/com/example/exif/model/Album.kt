package com.example.exif.model.exif

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//アルバムテーブル

@Entity(tableName = "album")
data class Album (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "album_name") val albumName: String,
    @ColumnInfo(name = "create_date") val createDate: Long,
    @ColumnInfo(name = "update_name") val updateDate: Long?
)