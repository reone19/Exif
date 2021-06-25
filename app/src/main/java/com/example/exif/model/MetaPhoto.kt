package com.example.exif.model.exif

import androidx.room.*

//1対１のオブジェクトリレーション
//メタと写真、メタと写真のエンティティも定義しないといけないため、
// それぞれのエンティティクラスを作っている。

@Entity(tableName = "meta")
data class MetaPhoto (
    @Embedded val photo: Photo,
    @Relation(
        parentColumn ="id",
        entityColumn ="photoMetaId"
    )
    @ColumnInfo(name = "image_width") val imageWidth: Meta,
    @ColumnInfo(name = "image_length") val imageLength: Meta,
    @ColumnInfo(name = "maker_name") val makerName: Meta,
    @ColumnInfo(name = "img_dir") val imgDir: Meta,
    @ColumnInfo(name = "update_date") val updateDate: Meta,
    @ColumnInfo(name = "free_char") val freeChar: Meta
)