package com.example.exif.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Photo : RealmObject(){
    @PrimaryKey
    var photoId: Long = 0

    @Required
    var name: String? = null //写真名

    @Required
    var url: String? = null //URL
    var deleteDate: Date? = null //削除日
    //var meta: Meta? = null//メタと１対１
    var album: RealmList<Album>? = RealmList() //アルバムと多対多
}