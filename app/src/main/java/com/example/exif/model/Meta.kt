package com.example.exif.model

import io.realm.RealmObject
import java.util.*

// メタテーブル
open class Meta : RealmObject() {
    var imageWidth: Int? = null    // 画像横幅
    var imageLength: Int? = null   // 画像の高さ
    var makerName: String? = null  // メーカ名
    var imageDir: String? = null   // 画像の方向
    var updateDate: Date? = null   // 変更日時
    var freeChar: String? = null   // 任意の文字列

    var metaDetail: MetaDetail? = null  // MetaDetailテーブルと1対1の関係
    var metaPro: MetaPro? = null        // MetaProテーブルと1対1の関係
}
