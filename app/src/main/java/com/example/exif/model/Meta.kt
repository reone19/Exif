package com.example.exif.model.exif

import androidx.room.*

//語尾の?は多分NULL制約

@Entity(tableName = "meta")
data class Meta(
    @PrimaryKey val metaId: Int,
    @ColumnInfo(name = "image_width") val imageWidth: Int?,
    @ColumnInfo(name = "image_length") val imageLength: Int?,
    @ColumnInfo(name = "maker_name") val makerName: String?,
    @ColumnInfo(name = "img_dir") val imgDir: String?,
    @ColumnInfo(name = "update_date") val updateDate: Long?,
    @ColumnInfo(name = "free_char") val freeChar: String?
)
