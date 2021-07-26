package com.example.exif

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class AlbumFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    //アルバムレイアウトカウンタ(横に3つImageBottonを作る)
    var a:Int = 0
    //アルバムID用カウンタ(配列)
    var b:Int = 0
    //アルバム個数カウンタ
    var c:Int = 1
    //DB内のアルバム個数
    var d:Int = 0
    //表示用アルバムレイアウトカウンタ
    var e:Int = 0
    //アルバムID
    var f:Int = 1
    //テキストID
    var g:Int = 2
    //サブテキストID
    var j:Int = 3
    //arrayList用添え字
    var h:Int = 0
    //btn用添え字
    var k:Int = 0

    var arrayListId: ArrayList<String> = arrayListOf()
    var arrayListTextId: ArrayList<String> = arrayListOf()
    var arrayListSubTextId: ArrayList<String> = arrayListOf()
    var arrayListPhotoNum: ArrayList<Int> = arrayListOf()
    var arrayListName: ArrayList<String> = arrayListOf()
    var arrayListNum: ArrayList<Int> = arrayListOf()

    companion object {
        private const val TAG = "AlbumFragment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ポップアップをインスタンス化、非表示
        val popup = view.findViewById<LinearLayout>(R.id.popup)
        popup.visibility = View.INVISIBLE

        //ポップアップ時背景用インスタンス化
        val scroll = view.findViewById<ScrollView>(R.id.scroll)

        //アルバム遷移用ボタンのインスタンス化準備
        val btn = arrayOfNulls<ImageButton>(999)

        //データベース接続
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        val sql =
            "select id, text_id, subtext_id, photo_num, name, num from " + "Album"
        val cursor = databaseR.rawQuery(sql, null)
        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                arrayListId.add(cursor.getString(0))
                arrayListTextId.add(cursor.getString(1))
                arrayListSubTextId.add(cursor.getString(2))
                arrayListPhotoNum.add(cursor.getInt(3))
                arrayListName.add(cursor.getString(4))
                arrayListNum.add(cursor.getInt(5))
                cursor.moveToNext()
            }
            //アルバム個数取得
            d = arrayListNum.size

            //album_sub追加処理
            for (i in 0..d/3){
                //「MainLinearLayout」というidに「album_sub.xml」を追加
                val Main_sub = view.findViewById<LinearLayout>(R.id.MainLinearLayout)
                layoutInflater.inflate(R.layout.album_sub,Main_sub)

                //アルバム1つ1つの設定ループ
                for (q in 0..2){
                    //IDを変数で指定
                    val image = "image_id$e"
                    val text = "text$e"
                    val sub_text = "sub_text$e"
                    val imageId = resources.getIdentifier(image, "id", "com.example.exif")
                    val textId = resources.getIdentifier(text, "id", "com.example.exif")
                    val sub_textId = resources.getIdentifier(sub_text, "id", "com.example.exif")
                    //空の写真をインスタンス化
                    val imagesourse = resources.getIdentifier("noimage", "drawable", "com.example.exif")

                    //album_subの追加がラストか判別(ラストじゃないなら)
                    if (i != d/3){
                        //データベースから取得したIDをセットする
                        val imageview = view.findViewById<ImageButton>(imageId)
                        val textview = view.findViewById<TextView>(textId)
                        val sub_textview = view.findViewById<TextView>(sub_textId)
                        imageview.setId(arrayListId.get(h).toInt())
                        textview.setId(arrayListTextId.get(h).toInt())
                        sub_textview.setId(arrayListSubTextId.get(h).toInt())

                        //アルバム遷移用ボタンのインスタンス化
                        btn[k] = view.findViewById<ImageButton>(arrayListId.get(h).toInt())
                        btn[k]?.setOnClickListener{
                            val intent = Intent(context, AlbumPhotoFragment::class.java)
                            startActivity(intent)
                        }
                        //アルバムタイトル表示
                        val tv = view.findViewById<TextView>(arrayListTextId.get(h).toInt())
                        tv.setText(arrayListName.get(h))
                        //アルバム内の写真数表示
                        val sub_tv = view.findViewById<TextView>(arrayListSubTextId.get(h).toInt())
                        sub_tv.setText(arrayListPhotoNum.get(h).toString())
                        //空の写真をnoimageに
                        imageview.setImageResource(imagesourse)
                    }
                    else{
                        try {
                            //データベースから取得したIDをセットする
                            val imageview = view.findViewById<ImageButton>(imageId)
                            val textview = view.findViewById<TextView>(textId)
                            val sub_textview = view.findViewById<TextView>(sub_textId)
                            imageview.setId(arrayListId.get(h).toInt())
                            textview.setId(arrayListTextId.get(h).toInt())
                            sub_textview.setId(arrayListSubTextId.get(h).toInt())

                            //アルバム遷移用ボタンのインスタンス化
                            btn[k] = view.findViewById<ImageButton>(arrayListId.get(h).toInt())
                            btn[k]?.setOnClickListener{
                                val intent = Intent(context, AlbumPhotoFragment::class.java)
                                startActivity(intent)
                            }
                            //アルバムタイトル表示
                            val tv = view.findViewById<TextView>(arrayListTextId.get(h).toInt())
                            tv.setText(arrayListName.get(h))
                            //アルバム内の写真数表示
                            val sub_tv = view.findViewById<TextView>(arrayListSubTextId.get(h).toInt())
                            sub_tv.setText(arrayListPhotoNum.get(h).toString())
                            //空の写真をnoimageに
                            imageview.setImageResource(imagesourse)
                        }
                        catch (e: IndexOutOfBoundsException){
                            break
                        }
                    }
                    //アルバムIDを取得
                    f = arrayListId.get(h).toInt() + 3
                    //テキストIDを取得
                    g = arrayListTextId.get(h).toInt() + 3
                    //サブテキストIDを取得
                    j = arrayListSubTextId.get(h).toInt() + 3
                    e = e + 1
                    h = h + 1
                    k = k + 1
                    //1列追加したらカウンタを0に
                    if (e == 3){
                        e = 0;
                    }
                }
                if (d%3 == 0){
                    break
                }
            }
            a = a + (d % 3)
            c = c + d
        }
        
        //「追加」を押したときにポップアップを追加
        val addButton = view.findViewById<Button>(R.id.add_album)
        addButton.setOnClickListener{
            //ポップアップを表示
            popup.visibility = View.VISIBLE
            //背景を暗く
            scroll.setBackgroundColor(Color.argb(100,0,0,0))
        }

        //「保存」を押したときにアルバムを追加
        val saveBtn = view.findViewById<Button>(R.id.btn_save)
        saveBtn.setOnClickListener{
            //IDを変数で指定
            val image = "image_id$a"
            val text = "text$a"
            val sub_text = "sub_text$a"
            val imageId = resources.getIdentifier(image, "id", "com.example.exif")
            val textId = resources.getIdentifier(text, "id", "com.example.exif")
            val sub_textId = resources.getIdentifier(sub_text, "id", "com.example.exif")
            //空の写真をインスタンス化
            val imagesourse = resources.getIdentifier("noimage", "drawable", "com.example.exif")
            //新しいID生成
            val imagenewId = IntArray(999)
            imagenewId[b] = f
            val textnewId = IntArray(999)
            textnewId[b] = g
            val sub_textnewId = IntArray(999)
            sub_textnewId[b] = j

            val title: String
            if (a%3 == 0){
                //「MainLinearLayout」というidに「album_sub.xml」を追加
                val Main_sub = view.findViewById<LinearLayout>(R.id.MainLinearLayout)
                layoutInflater.inflate(R.layout.album_sub,Main_sub)

                //新しく生成したIDをセットする
                val imageview = view.findViewById<ImageButton>(imageId)
                val textview = view.findViewById<TextView>(textId)
                val sub_textview = view.findViewById<TextView>(sub_textId)
                imageview.setId(imagenewId[b])
                textview.setId(textnewId[b])
                sub_textview.setId(sub_textnewId[b])

                //アルバム遷移用ボタンのインスタンス化
                btn[k] = view.findViewById<ImageButton>(imagenewId[b])
                btn[k]?.setOnClickListener{
                    val intent = Intent(context, AlbumPhotoFragment::class.java)
                    startActivity(intent)
                }
                val tv = view.findViewById<TextView>(textnewId[b])
                val sub_tv = view.findViewById<TextView>(sub_textnewId[b])

                val edittext = view.findViewById<EditText>(R.id.editTexttitle)
                title = edittext.text.toString()
                tv.setText(title)
                sub_tv.setText("0")
            }
            else{
                //新しく生成したIDをセットする
                val imageview = view.findViewById<ImageButton>(imageId)
                val textview = view.findViewById<TextView>(textId)
                val sub_textview = view.findViewById<TextView>(sub_textId)
                imageview.setId(imagenewId[b])
                textview.setId(textnewId[b])
                sub_textview.setId(sub_textnewId[b])

                //アルバム遷移用ボタンのインスタンス化
                btn[k] = view.findViewById<ImageButton>(imagenewId[b])
                btn[k]?.setOnClickListener{
                    val intent = Intent(context, AlbumPhotoFragment::class.java)
                    startActivity(intent)
                }
                val tv = view.findViewById<TextView>(textnewId[b])
                val sub_tv = view.findViewById<TextView>(sub_textnewId[b])

                val edittext = view.findViewById<EditText>(R.id.editTexttitle)
                title = edittext.text.toString()
                tv.setText(title)
                sub_tv.setText("0")
                //空の写真をnoimageに
                imageview.setImageResource(imagesourse)
            }

            // データの挿入処理
            val database = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("id", imagenewId[b])
            values.put("text_id", textnewId[b])
            values.put("subtext_id", sub_textnewId[b])
            values.put("photo_num", 0)
            values.put("name", title)
            values.put("num", c)
            database.insertOrThrow("Album", null, values)

            a = a + 1
            b = b + 1
            c = c + 1
            f = f + 3
            g = g + 3
            j = j + 3
            k = k + 1

            //1列追加したらカウンタを0に
            if (a == 3){
                a = 0;
            }
            popup.visibility = View.INVISIBLE
            scroll.setBackgroundColor(Color.argb(255,255,255,255))
        }

        //「キャンセル」を押したときにポップアップを非表示に
        val cancelBtn = view.findViewById<Button>(R.id.btn_cancel)
        cancelBtn.setOnClickListener{
            //ポップアップを非表示
            popup.visibility = View.INVISIBLE
            scroll.setBackgroundColor(Color.argb(255,255,255,255))
        }
    }
}