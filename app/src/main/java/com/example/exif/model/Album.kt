package com.example.exif.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

// アルバムテーブル
open class Album : RealmObject() {
    @PrimaryKey
    var id: Long = 0               // アルバムID

    @Required
    var name: String? = null       // アルバム名

    @Required
    var createDate: Date = Date()  // 作成日
    var updateDate: Date? = null   // 更新日

    var photos: RealmList<Photo>? = RealmList()  // Photoテーブルと多対多の関係
}
