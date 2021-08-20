package com.example.exif

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exif.model.Image
import com.example.exif.model.PhotoAdapter

class SearchResultActivity : AppCompatActivity() {

    // フィールドの記載
    private var imageRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var allPictures: ArrayList<Image>? = null

    var a: Int = 0
    var b: Int = 1

    var arrayListPhotoId: java.util.ArrayList<String> = arrayListOf()
    var arrayListAlbumId: java.util.ArrayList<String> = arrayListOf()
    var arrayListPhotoPath: java.util.ArrayList<String> = arrayListOf()
    var arrayListPhotoName: java.util.ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // リサイクルビューイメージのId定義
        imageRecycler = findViewById(R.id.image_recycler)
        progressBar = findViewById(R.id.recycler_progress)
        // リサイクルビューのグリットレイアウトで表示されている画像の制御、spanCountは4列で画像を並べてる意味
        imageRecycler?.layoutManager = GridLayoutManager(this, 4)
        // これで表示画像の大きさを均等になるよう修正を加えている。falseにしたら大変な事になる。
        imageRecycler?.setHasFixedSize(true)

        if (ContextCompat.checkSelfPermission(
                this@SearchResultActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@SearchResultActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101
            )
        }



        var result = intent.getStringExtra("result")

        //データベース接続
        val dbHelper = SampleDBHelper(this, "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        val sql =
            "SELECT * FROM Meta WHERE photo_id LIKE '%$result%' OR image_name LIKE '%$result%' OR image_length LIKE '%$result%' OR image_width LIKE '%$result%' OR y_resolution LIKE '%$result%' OR x_resolution LIKE '%$result%' OR bits_per_sample LIKE '%$result%' OR compression LIKE '%$result%' OR image_orientation LIKE '%$result%' OR image_description LIKE '%$result%' OR artist LIKE '%$result%' OR maker LIKE '%$result%' OR model LIKE '%$result%' OR aperture LIKE '%$result%' OR exposure_time LIKE '%$result%' OR iso_speed LIKE '%$result%' OR exposure_bias LIKE '%$result%' OR f_number LIKE '%$result%' OR shutter_speed LIKE '%$result%' OR focal_length LIKE '%$result%' OR metering_mode LIKE '%$result%' OR flash LIKE '%$result%' OR strip_offsets LIKE '%$result%' OR gps_version_id LIKE '%$result%' OR gps_latitude LIKE '%$result%' OR gps_longitude LIKE '%$result%' OR gps_altitude LIKE '%$result%' OR date_time_original LIKE '%$result%' OR change_date_and_time LIKE '%$result%'"
        val dbCursor = databaseR.rawQuery(sql, null)

        if (dbCursor.count > 0) {
            dbCursor.moveToFirst()
            while (!dbCursor.isAfterLast) {
                arrayListPhotoId.add(dbCursor.getString(0))
                arrayListPhotoName.add(dbCursor.getString(1))
                arrayListPhotoPath.add(dbCursor.getString(2))
                arrayListAlbumId.add(dbCursor.getString(3))
                // arrayListAlbumId.add(cursor.getString(1))
                Log.d("TAG", arrayListPhotoId[a])
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
            arrayOf(
                MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.MediaColumns.SIZE)

        var cursor =
            this@SearchResultActivity.contentResolver.query(
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