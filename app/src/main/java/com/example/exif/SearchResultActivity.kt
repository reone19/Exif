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
import android.widget.SearchView
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

        // 検索画面ではviewPager2をスライドさせない
        slideYesNo = false

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "検索結果"

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

        val result:ArrayList<String?> = arrayListOf()

        for (i in 1..30) {
            result.add(intent.getStringExtra("result$i"))
        }

        //データベース接続
        val dbHelper = SampleDBHelper(this, "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        var sql = ""

        if (result[0] != null) {
            val query1 = result[0]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE image_name LIKE '%$query1%'"
        }
        if (result[1] != null) {
            val query2 = result[1]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE image_length LIKE '$query2'"
        }
        if (result[2] != null) {
            val query3 = result[2]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE image_width LIKE '$query3'"
        }
        if (result[3] != null) {
            val query4 = result[3]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE y_resolution LIKE '$query4'"
        }
        if (result[4] != null) {
            val query5 = result[4]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE x_resolution LIKE '$query5'"
        }
        if (result[5] != null) {
            val query6 = result[5]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE bits_per_sample LIKE '$query6'"
        }
        if (result[6] != null) {
            val query7 = result[6]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE compression LIKE '$query7'"
        }
        if (result[7] != null) {
            val query8 = result[7]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE image_orientation LIKE '$query8'"
        }
        if (result[8] != null) {
            val query9 = result[8]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE image_description LIKE '%$query9%'"
        }
        if (result[9] != null) {
            val query10 = result[9]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE artist LIKE '%$query10%'"
        }
        if (result[10] != null) {
            val query11 = result[10]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE maker LIKE '%$query11%'"
        }
        if (result[11] != null) {
            val query12 = result[11]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE model LIKE '%$query12%'"
        }
        if (result[12] != null) {
            val query13 = result[12]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE aperture LIKE '%$query13%'"
        }
        if (result[13] != null) {
            val query14 = result[13]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE exposure_time LIKE '$query14'"
        }
        if (result[14] != null) {
            val query15 = result[14]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE iso_speed LIKE '$query15'"
        }
        if (result[15] != null) {
            val query16 = result[15]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE exposure_bias LIKE '$query16'"
        }
        if (result[16] != null) {
            val query17 = result[16]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE f_number LIKE '$query17'"
        }
        if (result[17] != null) {
            val query18 = result[17]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE shutter_speed LIKE '%$query18%'"
        }
        if (result[18] != null) {
            val query19 = result[18]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE focal_length LIKE '$query19'"
        }
        if (result[19] != null) {
            val query20 = result[19]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE metering_mode LIKE '$query20'"
        }
        if (result[20] != null) {
            val query21 = result[20]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE flash LIKE '$query21'"
        }
        if (result[21] != null) {
            val query22 = result[21]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE strip_offsets LIKE '$query22'"
        }
        if (result[22] != null) {
            val query23 = result[22]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE gps_version_id LIKE '$query23'"
        }
        if (result[23] != null) {
            val query24 = result[23]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE gps_latitude LIKE '$query24'"
        }
        if (result[24] != null) {
            val query25 = result[24]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE gps_longitude LIKE '$query25'"
        }
        if (result[25] != null) {
            val query26 = result[25]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE gps_altitude LIKE '$query26'"
        }
        if (result[26] != null) {
            val query27 = result[26]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE date_time_original LIKE '%$query27%'"
        }
        if (result[27] != null) {
            val query28 = result[27]
            sql =
                "SELECT photo_id, image_name FROM Meta WHERE change_date_and_time LIKE '%$query28%'"
        }
        if (result[28] != null) {
            val query29 = result[28]
            sql =
                "SELECT id, name  FROM Photo WHERE ocr LIKE '%$query29%'"
        }
        if (result[29] != null) {
            val query30 = result[29]
            sql =
                "SELECT id, name  FROM Photo WHERE sentence1 LIKE '%$query30%' OR sentence2 LIKE '%$query30%' OR sentence3 LIKE '%$query30%'"
        }

        val dbCursor = databaseR.rawQuery(sql, null)

        if (dbCursor.count > 0) {
            dbCursor.moveToFirst()
            while (!dbCursor.isAfterLast) {
                arrayListPhotoId.add(dbCursor.getString(0))
                arrayListPhotoName.add(dbCursor.getString(1))
//                arrayListPhotoPath.add(dbCursor.getString(2))
//                arrayListAlbumId.add(dbCursor.getString(3))
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