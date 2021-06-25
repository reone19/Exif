package com.example.exif.model.exif

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExifDao {

    //写真の処理




    //アルバムの処理
    @Insert
    fun insert(albumName: Album, createDate: Album)
    fun insert(photoName: Photo)
    @Update
    fun update(updateDate: Album)
    @Delete

    //アルバムリストの抽出
    @Query("select* from album")
    suspend fun getAlbumList(): List<AlbumPhoto>

    @Query("select photo_name from photo")
    suspend fun getPhotoList(): List<Photo>

    //Exifの処理

}