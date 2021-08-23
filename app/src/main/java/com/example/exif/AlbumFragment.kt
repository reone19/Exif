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


class AlbumFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.plus, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

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

    var arrayListId: ArrayList<String> = arrayListOf()
    var arrayListTextId: ArrayList<String> = arrayListOf()
    var arrayListSubTextId: ArrayList<String> = arrayListOf()
    var arrayListPhotoNum: ArrayList<Int> = arrayListOf()
    var arrayListName: ArrayList<String> = arrayListOf()
    var arrayListNum: ArrayList<Int> = arrayListOf()

    var arrayListAlbumPhotoNum: ArrayList<String> = arrayListOf()

    var arrayListImagePath: ArrayList<String> = arrayListOf()

    lateinit var popup: LinearLayout
    lateinit var scroll: ScrollView


    companion object {
        private const val TAG = "AlbumFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //サポートバー設定
        (activity as AppCompatActivity).supportActionBar?.title = "アルバム"

        // ポップアップをインスタンス化、非表示
        popup = view.findViewById<LinearLayout>(R.id.popup)
        popup.visibility = View.INVISIBLE

        // ポップアップ時背景用インスタンス化
        scroll = view.findViewById<ScrollView>(R.id.scroll)

        // アルバム遷移用ボタンのインスタンス化準備
        val btn = arrayOfNulls<ImageButton>(999)
        val num = arrayOfNulls<Int>(999)

        // データベース接続
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        val sql =
            "SELECT id, text_id, subtext_id, photo_num, name, num FROM " + "Album"
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

            // アルバム個数取得
            d = arrayListNum.size

            // album_sub追加処理
            for (i in 0..d / 3) {
                //「MainLinearLayout」というidに「album_sub.xml」を追加
                val mainSub = view.findViewById<LinearLayout>(R.id.MainLinearLayout)
                layoutInflater.inflate(R.layout.album_sub, mainSub)

                // アルバム1つ1つの設定ループ
                for (q in 0..2) {
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
                    if (i != d / 3) {
                        // データベースから取得したIDをセットする
                        val imageview = view.findViewById<ImageButton>(imageId)
                        val textview = view.findViewById<TextView>(textId)
                        val subTextview = view.findViewById<TextView>(subTextId)

                        imageview.id = arrayListId[h].toInt()
                        textview.id = arrayListTextId[h].toInt()
                        subTextview.id = arrayListSubTextId[h].toInt()

                        // アルバム遷移用ボタンのインスタンス化
                        btn[k] = view.findViewById<ImageButton>(arrayListId[h].toInt())
                        num[k] = arrayListId[h].toInt()

                        // アルバムタイトル表示
                        val tv = view.findViewById<TextView>(arrayListTextId[h].toInt())
                        tv.text = arrayListName[h]
                        // アルバム内の写真数表示
                        val subTv = view.findViewById<TextView>(arrayListSubTextId[h].toInt())

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
                        try {
                            // データベースから取得したIDをセットする
                            val imageview = view.findViewById<ImageButton>(imageId)
                            val textview = view.findViewById<TextView>(textId)
                            val subTextview = view.findViewById<TextView>(subTextId)

                            imageview.id = arrayListId[h].toInt()
                            textview.id = arrayListTextId[h].toInt()
                            subTextview.id = arrayListSubTextId[h].toInt()

                            // アルバム遷移用ボタンのインスタンス化
                            btn[k] = view.findViewById<ImageButton>(arrayListId[h].toInt())
                            num[k] = arrayListId[h].toInt()
                            // アルバムタイトル表示
                            val tv = view.findViewById<TextView>(arrayListTextId[h].toInt())
                            tv.text = arrayListName[h]
                            // アルバム内の写真数表示
                            val subTv =
                                view.findViewById<TextView>(arrayListSubTextId[h].toInt())

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

                        } catch (e: IndexOutOfBoundsException) {
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

                    // 1列追加したらカウンタを0に
                    if (e == 3) {
                        e = 0;
                    }
                    arrayListImagePath.clear()
                }
                if (d <= l && d % l == 0) {
                    break
                }
                l += 3

            }
            a += (d % 3)
            c += d
        }

        // 「追加」を押したときにポップアップを追加
        //val addButton = view.findViewById<Button>(R.id.add_album)
        //addButton.setOnClickListener {
        // ポップアップを表示
        //popup.visibility = View.VISIBLE
        // 背景を暗く
        //scroll.setBackgroundColor(Color.argb(100, 0, 0, 0))
        //}

        // 「保存」を押したときにアルバムを追加
        val saveBtn = view.findViewById<Button>(R.id.btn_save)
        saveBtn.setOnClickListener {
            // IDを変数で指定
            val image = "image_id$a"
            val text = "text$a"
            val subText = "sub_text$a"
            val imageId = resources.getIdentifier(image, "id", "com.example.exif")
            val textId = resources.getIdentifier(text, "id", "com.example.exif")
            val subTextId = resources.getIdentifier(subText, "id", "com.example.exif")
            // 空の写真をインスタンス化
            val imageSource = resources.getIdentifier("picture", "drawable", "com.example.exif")
            // 新しいID生成
            val imageNewId = IntArray(999)
            imageNewId[b] = f
            val textNewId = IntArray(999)
            textNewId[b] = g
            val subTextNewId = IntArray(999)
            subTextNewId[b] = j

            val title: String
            if (a % 3 == 0) {
                // 「MainLinearLayout」というidに「album_sub.xml」を追加
                val mainSub = view.findViewById<LinearLayout>(R.id.MainLinearLayout)
                layoutInflater.inflate(R.layout.album_sub, mainSub)

                // 新しく生成したIDをセットする
                val imageview = view.findViewById<ImageButton>(imageId)
                val textview = view.findViewById<TextView>(textId)
                val subTextview = view.findViewById<TextView>(subTextId)

                imageview.id = imageNewId[b]
                textview.id = textNewId[b]
                subTextview.id = subTextNewId[b]

                // アルバム遷移用ボタンのインスタンス化
                btn[k] = view.findViewById<ImageButton>(imageNewId[b])
                num[k] = imageNewId[b]

                val tv = view.findViewById<TextView>(textNewId[b])
                val subTv = view.findViewById<TextView>(subTextNewId[b])
                val edittext = view.findViewById<EditText>(R.id.editTexttitle)

                title = edittext.text.toString()
                tv.text = title
                subTv.text = "0"

            } else {
                // 新しく生成したIDをセットする
                val imageview = view.findViewById<ImageButton>(imageId)
                val textview = view.findViewById<TextView>(textId)
                val subTextview = view.findViewById<TextView>(subTextId)

                imageview.id = imageNewId[b]
                textview.id = textNewId[b]
                subTextview.id = subTextNewId[b]

                // アルバム遷移用ボタンのインスタンス化
                btn[k] = view.findViewById(imageNewId[b])
                num[k] = imageNewId[b]

                val tv = view.findViewById<TextView>(textNewId[b])
                val subTv = view.findViewById<TextView>(subTextNewId[b])
                val edittext = view.findViewById<EditText>(R.id.editTexttitle)

                title = edittext.text.toString()
                tv.text = title
                subTv.text = "0"
                // 空の写真をpictureに
                imageview.setImageResource(imageSource)

            }

            // データの挿入処理
            val database = dbHelper.writableDatabase
            val values = ContentValues()

            values.put("id", imageNewId[b])
            values.put("text_id", textNewId[b])
            values.put("subtext_id", subTextNewId[b])
            values.put("photo_num", 0)
            values.put("name", title)
            values.put("num", c)

            database.insertOrThrow("Album", null, values)

            Toast.makeText(context, "アルバムが作成されました", Toast.LENGTH_LONG).show()
            val intent = Intent(context, AlbumDetailActivity::class.java)
            intent.putExtra("album_id", f.toString())
            intent.putExtra("intent_flg", "0")
            intent.putExtra("name", title)
            startActivity(intent)

            a += 1
            b += 1
            c += 1
            f += 3
            g += 3
            j += 3
            k += 1

            // 1列追加したらカウンタを0に
            if (a == 3) {
                a = 0;
            }
            popup.visibility = View.INVISIBLE
            scroll.setBackgroundColor(Color.argb(255, 255, 255, 255))
        }

        // 「キャンセル」を押したときにポップアップを非表示に
        val cancelBtn = view.findViewById<Button>(R.id.btn_cancel)
        cancelBtn.setOnClickListener {
            // ポップアップを非表示
            popup.visibility = View.INVISIBLE
            scroll.setBackgroundColor(Color.argb(255, 255, 255, 255))
        }

        for (z in 0..c) {
            btn[z]?.setOnClickListener {
                Log.d("TAG", btn[z].toString())
                val intent = Intent(context, AlbumDetailActivity::class.java)
                intent.putExtra("album_id", num[z].toString())
                intent.putExtra("intent_flg", "1")
                intent.putExtra("name", arrayListName[z])
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            //作成ボタンを押したとき
            R.id.addButton -> {
                // ポップアップを表示
                popup.visibility = View.VISIBLE
                // 背景を暗く
                scroll.setBackgroundColor(Color.argb(100, 0, 0, 0))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}