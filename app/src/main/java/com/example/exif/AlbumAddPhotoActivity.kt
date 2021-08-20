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
import com.example.exif.model.AlbumAdapter
import com.example.exif.model.Image
import kotlinx.android.synthetic.main.activity_main.*


class AlbumAddPhotoActivity : AppCompatActivity() {
    var a: Int = 1
    var albumID = ""

    // フィールドの記載
    private var imageRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var allPictures: ArrayList<Image>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_add_photo)

        albumID = intent.getStringExtra("album_id").toString()
        Log.d("AlbumId", albumID)

//        val okButton = findViewById<Button>(R.id.ok)
//        okButton.setOnClickListener {
//            Toast.makeText(this, "アルバムに写真が追加がされました", Toast.LENGTH_LONG).show()
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // リサイクルビューイメージのId定義
        imageRecycler = findViewById(R.id.image_recycler)
        progressBar = findViewById(R.id.recycler_progress)
        // リサイクルビューのグリットレイアウトで表示されている画像の制御、spanCountは4列で画像を並べてる意味
        imageRecycler?.layoutManager = GridLayoutManager(this, 4)
        // これで表示画像の大きさを均等になるよう修正を加えている。falseにしたら大変な事になる。
        imageRecycler?.setHasFixedSize(true)

        if (ContextCompat.checkSelfPermission(
                this@AlbumAddPhotoActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@AlbumAddPhotoActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101
            )
        }

        allPictures = ArrayList()

        if (allPictures!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            // 画像取得の際のプログレスバーの不可視設定かつimageRecyclerに
            // allPicturesの画像配列をセット。
            allPictures = getAllImages()
            // Adapterをリサイクラーにセットする
            imageRecycler?.adapter = AlbumAdapter(this, allPictures!!)
            progressBar?.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        //メニューのリソース選択
        inflater.inflate(R.menu.ok, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            //作成ボタンを押したとき
            R.id.okButton -> {
                Toast.makeText(this, "アルバムに写真が追加がされました", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AlbumDetailActivity::class.java)
                intent.putExtra("album_id", albumID)
                intent.putExtra("intent_flg", "0")
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // アプリバーの戻るボタンを押したときにfinish
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    // 外部ストレージからすべての画像を取得するメソッドの設定
    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor =
            this@AlbumAddPhotoActivity.contentResolver.query(
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
                image.imageId = a.toString()
                image.albumId = albumID
                image.imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
                a += 1
            } while (cursor.moveToNext())
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images
    }
}