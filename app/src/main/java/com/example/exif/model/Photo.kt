package com.example.exif.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

// 写真テーブル
open class Photo : RealmObject() {
    @PrimaryKey
    var id: Long = 0              // 写真ID

    @Required
    var name: String? = null      // 写真名

    @Required
    var url: String? = null       // URL
    var deleteDate: Date? = null  // 削除日時

    var meta: Meta? = null                       // Metaテーブルと1対1の関係
    var albums: RealmList<Album>? = RealmList()  // Albumテーブルと多対多の関係
}
