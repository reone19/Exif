package com.example.exif

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.exif.databinding.FragmentExifBinding
import java.io.File
import java.util.*


class AlbumDeleteActivity : AppCompatActivity() {

    // アルバムレイアウトカウンタ(横に3つImageButtonを作る)
    var a: Int = 0

    // アルバムID用カウンタ(配列)
    var b: Int = 0

    // アルバム個数カウンタ
    var c: Int = 1

    // DB内のアルバム個数
    var d: Int = 0

    // 表示用アルバムレイアウトカウンタ
    var e: Int = 0

    // アルバムID
    var f: Int = 1

    // テキストID
    var g: Int = 2

    // サブテキストID
    var j: Int = 3

    // arrayList用添え字
    var h: Int = 0

    // btn用添え字
    var k: Int = 0

    // 3の倍数用
    var l: Int = 3

    // delete用
    var x: Int = 0

    var minus: Int = 0

    // for文制御
    var p: Int = 2

    // for文制御
    var m: Int = 0

    var n: Int = 0

    var s: Int = 0

    var arrayListId: ArrayList<String> = arrayListOf()
    var arrayListTextId: ArrayList<String> = arrayListOf()
    var arrayListSubTextId: ArrayList<String> = arrayListOf()
    var arrayListDeleteFlg: ArrayList<Int> = arrayListOf()
    var arrayListName: ArrayList<String> = arrayListOf()
    var arrayListNum: ArrayList<Int> = arrayListOf()

    var arrayListAlbumPhotoNum: ArrayList<String> = arrayListOf()

    var arrayListImagePath: ArrayList<String> = arrayListOf()

    lateinit var popup: LinearLayout
    lateinit var scroll: ScrollView

    companion object {
        private const val TAG = "AlbumFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_delete)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "アルバム削除"

        // ポップアップをインスタンス化、非表示
        popup = findViewById<LinearLayout>(R.id.popup)
        popup.visibility = View.INVISIBLE

        // ポップアップ時背景用インスタンス化
        scroll = findViewById<ScrollView>(R.id.scroll)

        // アルバム遷移用ボタンのインスタンス化準備
        val btn = arrayOfNulls<ImageButton>(999)
        val num = arrayOfNulls<Int>(999)

        // データベース接続
        val dbHelper = SampleDBHelper(this, "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        val sql =
            "SELECT id, text_id, subtext_id, delete_flg, name, num FROM " + "Album"
        val cursor = databaseR.rawQuery(sql, null)

        if (cursor.count > 0) {
            cursor.moveToFirst()

            while (!cursor.isAfterLast) {
                arrayListId.add(cursor.getString(0))
                arrayListTextId.add(cursor.getString(1))
                arrayListSubTextId.add(cursor.getString(2))
                arrayListDeleteFlg.add(cursor.getInt(3))
                arrayListName.add(cursor.getString(4))
                arrayListNum.add(cursor.getInt(5))
                cursor.moveToNext()
            }
            for (i in 0..arrayListDeleteFlg.count() - 1) {
                if (arrayListDeleteFlg[i] == 1) {
                    minus = minus + 1
                }
            }

            // アルバム個数取得
            d = arrayListNum.size
            s = arrayListNum.size
            s = s - minus

            // album_sub追加処理
            for (i in 0..s / 3) {
                //「MainLinearLayout」というidに「album_sub.xml」を追加
                val mainSub = findViewById<LinearLayout>(R.id.MainLinearLayout)
                layoutInflater.inflate(R.layout.album_sub, mainSub)

                for (y in m..arrayListDeleteFlg.count() - 1) {
                    m = m + 1
                    if (arrayListDeleteFlg[y] == 1) {
                        p = p + 1
                    } else if (arrayListDeleteFlg[y] == 0) {
                        n = n + 1
                    }

                    if (n == 3) {
                        n = 0
                        break
                    }
                }

                // アルバム1つ1つの設定ループ
                for (q in 0..p) {
                    try {
                        val sqlSub =
                            "SELECT COUNT(*) FROM Album_Photo WHERE album_id = " + arrayListId[h]
                        val sqlImage =
                            "SELECT ph.Path FROM Album_Photo al INNER JOIN Photo ph ON al.photo_id=ph.id WHERE album_id = " + arrayListId[h]
                        val cursorSub = databaseR.rawQuery(sqlSub, null)
                        val cursorImage = databaseR.rawQuery(sqlImage, null)

                        if (cursorSub.count > 0) {
                            cursorSub.moveToFirst()

                            while (!cursorSub.isAfterLast) {
                                arrayListAlbumPhotoNum.add(cursorSub.getString(0))
                                cursorSub.moveToNext()
                            }
                        }
                        if (cursorImage.count > 0) {
                            cursorImage.moveToFirst()

                            while (!cursorImage.isAfterLast) {
                                arrayListImagePath.add(cursorImage.getString(0))
                                cursorImage.moveToNext()
                            }
                        }

                    } catch (e: IndexOutOfBoundsException) {

                    }
                    // IDを変数で指定
                    val image = "image_id$e"
                    val text = "text$e"
                    val subText = "sub_text$e"
                    val imageId = resources.getIdentifier(image, "id", "com.example.exif")
                    val textId = resources.getIdentifier(text, "id", "com.example.exif")
                    val subTextId = resources.getIdentifier(subText, "id", "com.example.exif")
                    // 空の写真をインスタンス化
                    val imageSource =
                        resources.getIdentifier("picture", "drawable", "com.example.exif")

                    // album_subの追加がラストか判別(ラストじゃないなら)
                    if (i != s / 3) {
                        try {
                            if (arrayListDeleteFlg[h] == 0) {
                                // データベースから取得したIDをセットする
                                val imageview = findViewById<ImageButton>(imageId)
                                val textview = findViewById<TextView>(textId)
                                val subTextview = findViewById<TextView>(subTextId)

                                imageview.id = arrayListId[h].toInt()
                                textview.id = arrayListTextId[h].toInt()
                                subTextview.id = arrayListSubTextId[h].toInt()

                                // アルバム遷移用ボタンのインスタンス化
                                btn[k] = findViewById<ImageButton>(arrayListId[h].toInt())
                                num[k] = arrayListId[h].toInt()

                                // アルバムタイトル表示
                                val tv = findViewById<TextView>(arrayListTextId[h].toInt())
                                tv.text = arrayListName[h]
                                // アルバム内の写真数表示
                                val subTv = findViewById<TextView>(arrayListSubTextId[h].toInt())

                                try {
                                    subTv.text = arrayListAlbumPhotoNum[h]
                                } catch (e: IndexOutOfBoundsException) {
                                    subTv.text = "0"
                                }
                                // 空の写真をpictureに
                                try {
                                    val file: File = File(arrayListImagePath[0])
                                    val uri = Uri.fromFile(file)
                                    imageview.setImageURI(uri)
                                } catch (e: IndexOutOfBoundsException) {
                                    imageview.setImageResource(imageSource)
                                }
                            } else {
                                e = e - 1
                            }
                        } catch (e: IndexOutOfBoundsException) {
                            h = h - 1
                        }

                    } else {
                        try {
                            if (arrayListDeleteFlg[h] == 0) {
                                // データベースから取得したIDをセットする
                                val imageview = findViewById<ImageButton>(imageId)
                                val textview = findViewById<TextView>(textId)
                                val subTextview = findViewById<TextView>(subTextId)

                                imageview.id = arrayListId[h].toInt()
                                textview.id = arrayListTextId[h].toInt()
                                subTextview.id = arrayListSubTextId[h].toInt()

                                // アルバム遷移用ボタンのインスタンス化
                                btn[k] = findViewById<ImageButton>(arrayListId[h].toInt())
                                num[k] = arrayListId[h].toInt()
                                // アルバムタイトル表示
                                val tv = findViewById<TextView>(arrayListTextId[h].toInt())
                                tv.text = arrayListName[h]
                                // アルバム内の写真数表示
                                val subTv =
                                    findViewById<TextView>(arrayListSubTextId[h].toInt())

                                try {
                                    subTv.text = arrayListAlbumPhotoNum[h]
                                } catch (e: IndexOutOfBoundsException) {
                                    subTv.text = "0"
                                }
                                // 空の写真をpictureに
                                try {
                                    val file: File = File(arrayListImagePath[0])
                                    val uri = Uri.fromFile(file)
                                    imageview.setImageURI(uri)
                                } catch (e: IndexOutOfBoundsException) {
                                    imageview.setImageResource(imageSource)
                                }
                            } else {
                                e = e - 1
                            }

                        } catch (e: IndexOutOfBoundsException) {
                            h = h - 1
                            break
                        }
                    }
                    // アルバムIDを取得
                    f = arrayListId[h].toInt() + 3
                    // テキストIDを取得
                    g = arrayListTextId[h].toInt() + 3
                    // サブテキストIDを取得
                    j = arrayListSubTextId[h].toInt() + 3

                    e += 1
                    h += 1
                    k += 1
                    p = 2

                    // 1列追加したらカウンタを0に
                    if (e == 3) {
                        e = 0;
                    }
                    arrayListImagePath.clear()
                }
                if (s <= l && s % l == 0) {
                    break
                }
                l += 3

            }
            a += (s % 3)
            c += d
        }

        // 「削除」を押したときにアルバムを削除
        val deleteBtn = findViewById<Button>(R.id.btn_delete)
        deleteBtn.setOnClickListener {
            val database = dbHelper.writableDatabase
            val values = ContentValues()

            values.put("delete_flg", 1)

            database.update("Album", values, "id=$x", null)

            Toast.makeText(this, "アルバムが削除されました", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            popup.visibility = View.INVISIBLE
            scroll.setBackgroundColor(Color.argb(255, 255, 255, 255))
        }

        // 「キャンセル」を押したときにポップアップを非表示に
        val cancelBtn = findViewById<Button>(R.id.btn_cancel)
        cancelBtn.setOnClickListener {
            // ポップアップを非表示
            popup.visibility = View.INVISIBLE
            scroll.setBackgroundColor(Color.argb(255, 255, 255, 255))
        }

        for (z in 0..c) {
            btn[z]?.setOnClickListener {
                x = arrayListId[z].toInt()
                // ポップアップを表示
                popup.visibility = View.VISIBLE
                // 背景を暗く
                scroll.setBackgroundColor(Color.argb(100, 0, 0, 0))
            }
        }
    }

    // アプリバーの戻るボタンを押したときにfinish
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}