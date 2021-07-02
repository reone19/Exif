package com.example.exif.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*


open class Album : RealmObject() {
    @PrimaryKey
    var id: Long = 0

    @Required
    var name: String? = null

    @Required
    var createDate: Date = Date()
    var updateDate: Date? = null

    var photo: RealmList<Photo>? = RealmList()

}