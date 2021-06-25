package com.example.exif.model.exif

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

//多対多オブジェクトリレーション

@Entity(
    primaryKeys =["photo_id","album_id"],
    foreignKeys = [
        ForeignKey(
            entity = Photo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("photo_id")
        ),
        ForeignKey(
            entity = Album::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("album_id")
        )]
)
data class AlbumPhoto (
    @ColumnInfo(name ="photo_id")
    val photoId: Int,
    @ColumnInfo(name ="album_id")
    val albumId: Int
)

