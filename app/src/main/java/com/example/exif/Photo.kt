package com.example.exif

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Photo (
    @PrimaryKey
    var photoId: Long = 0,
    var photoName: String = "",
    var photoURL: String = "",
    var deleteDate: Date = Date()
): RealmObject()