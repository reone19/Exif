package com.example.exif

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exif.model.Image
import com.example.exif.model.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*


class AlbumDetailActivity : AppCompatActivity() {
    var a: Int = 0
    var b: Int = 1
    var albumID = ""
    var intent_flg = ""
    var albumTitle = ""

    var arrayListPhotoId: java.util.ArrayList<String> = arrayListOf()
    var arrayListAlbumId: java.util.ArrayList<String> = arrayListOf()
    var arrayListPhotoPath: java.util.ArrayList<String> = arrayListOf()
    var arrayListPhotoName: java.util.ArrayList<String> = arrayListOf()

    // フィールドの記載
    private var imageRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var allPictures: ArrayList<Image>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        // アルバム画面ではviewPager2をスライドさせない
        slideYesNo = false

        albumID = intent.getStringExtra("album_id").toString()
        Log.d("TAG", albumID)

        albumTitle = intent.getStringExtra("name").toString()
        // データベース接続
        val dbHelper = SampleDBHelper(this, "SampleDB", null, 1)
        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        val sql =
            "SELECT al.photo_id, al.album_id, ph.path, ph.name FROM Album_Photo al INNER JOIN Photo ph ON al.photo_id=ph.id WHERE album_id = $albumID"
        val dbCursor = databaseR.rawQuery(sql, null)

//        val addButton = findViewById<Button>(R.id.add_photo)
//        addButton.setOnClickListener {
//            val intent = Intent(this, AlbumAddPhotoActivity::class.java)
//            intent.putExtra("album_id", albumID)
//            startActivity(intent)
//        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = albumTitle

        // リサイクルビューイメージのId定義
        imageRecycler = findViewById(R.id.image_recycler)
        progressBar = findViewById(R.id.recycler_progress)
        // リサイクルビューのグリットレイアウトで表示されている画像の制御、spanCountは4列で画像を並べてる意味
        imageRecycler?.layoutManager = GridLayoutManager(this, 4)
        // これで表示画像の大きさを均等になるよう修正を加えている。falseにしたら大変な事になる。
        imageRecycler?.setHasFixedSize(true)

        if (ContextCompat.checkSelfPermission(
                this@AlbumDetailActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@AlbumDetailActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101
            )
        }

        if (dbCursor.count > 0) {
            dbCursor.moveToFirst()
            while (!dbCursor.isAfterLast) {
                arrayListPhotoId.add(dbCursor.getString(0))
                arrayListAlbumId.add(dbCursor.getString(1))
                arrayListPhotoPath.add(dbCursor.getString(2))
                arrayListPhotoName.add(dbCursor.getString(3))
                // arrayListAlbumId.add(cursor.getString(1))
                a += 1
                dbCursor.moveToNext()
            }
        }

        allPictures = ArrayList()

        if (allPictures!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            // 画像取得の際のプログレスバーの不可視設定かつimageRecyclerに
            // allPicturesの画像配列をセット。
            allPictures = getAllImages()
            // Adapterをリサイクラーにセットする
            imageRecycler?.adapter = PhotoAdapter(this, allPictures!!)
            progressBar?.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        //メニューのリソース選択
        inflater.inflate(R.menu.plus, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            //作成ボタンを押したとき
            R.id.addButton -> {
                val intent = Intent(this, AlbumAddPhotoActivity::class.java)
                intent.putExtra("album_id", albumID)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // アプリバーの戻るボタンを押したときにfinish
    override fun onSupportNavigateUp(): Boolean {
        intent_flg = intent.getStringExtra("intent_flg").toString()
        Log.d("flg", intent_flg)
        if (intent_flg == "1") {
            intent_flg = "0"
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onSupportNavigateUp()
    }

    // 外部ストレージからすべての画像を取得するメソッドの設定
    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.MediaColumns.SIZE
            )

        var cursor =
            this@AlbumDetailActivity.contentResolver.query(
                allImageUri,
                projection,
                null,
                null,
                null
            )

        try {
            cursor!!.moveToFirst()
            do {
                val image = Image()
                image.imageId = b.toString()
                image.imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                image.imageSize =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))
                for (i in 0 until a) {
                    if (image.imageName == arrayListPhotoName[i]) {
                        images.add(image)
                    }
                }
                b += 1
            } while (cursor.moveToNext())
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images
    }
}