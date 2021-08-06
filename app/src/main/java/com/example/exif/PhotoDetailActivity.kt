package com.example.exif

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.exif.databinding.ActivityPhotoDetailBinding

// 画像のパス
var imagePath: String? = null
var imageName: String? = null
var photoID: String? = null


class PhotoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDetailBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 画像のパスを受け取るためのデータ
        imagePath = intent.getStringExtra("path")
        // 画像の名前を受け取るためのデータ
        imageName = intent.getStringExtra("name")
        photoID = intent.getStringExtra("id")
        val resultImage = findViewById<ImageView>(R.id.imageView)

        // アプリバーの表示
        // 戻るボタン
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // グリットレイアウトの画像のため、画像をGlideで画像のパスを取得、xmlの画像IDと紐づけて、画像を出力している。
        // 結果表示ImageViewの準備
//        Glide.with(this).load(imagePath).into(resultImage)

        binding.pager.adapter = MyAdapter(this)
    }


    class MyAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        @RequiresApi(Build.VERSION_CODES.N)
        override fun getItemCount(): Int = 4

        @RequiresApi(Build.VERSION_CODES.N)
        override fun createFragment(position: Int): Fragment =
            PhotoDetailFragment.newInstance(photoID?.toInt()!!)
    }


    // アプリバーの戻るボタンを押したときにfinish
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


//    //データベース接続
//    private val dbHelper = SampleDBHelper(this, "SampleDB", null, 1)
//    //外部ストレージからすべての画像を取得するメソッドの設定
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun getAllImages(): ArrayList<Image>?{
//        val images = ArrayList<Image>()
//        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val projection =
//            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)
//
//        var cursor =
//            this@PhotoDetailActivity.contentResolver.query(allImageUri, projection, null, null, null)
//
//        try {
//            cursor!!.moveToFirst()
//            do {
//                val image = Image()
//                image.imageId = a.toString()
//                image.imagePath =
//                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
//                image.imageName =
//                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
//                image.imageSentence1 = ""
//                image.imageSentence2 = ""
//                image.imageSentence3 = ""
//                try {
//                    val database = dbHelper.writableDatabase
//                    val values = ContentValues()
//                    values.put("id", a)
//                    values.put("path", image.imagePath)
//                    values.put("name", image.imageName)
//                    values.put("sentence1",image.imageSentence1)
//                    values.put("sentence2",image.imageSentence2)
//                    values.put("sentence3",image.imageSentence3)
//                    database.insertOrThrow("Photo",null, values)
//                }
//                catch (e: SQLiteConstraintException){
//
//                }
//
//                // Exif取得
//                val f: File = File(image.imagePath)
//                val uri = Uri.fromFile(f)
//                var `in`: InputStream? = null
//
//                try {
//                    `in` = contentResolver.openInputStream(uri)
//                    var exifInterface = ExifInterface(`in`!!)
//
//                    // Exifの各値をここにセット
//                    // <変数> = exifInterface.getAttribute(ExifInterface.<ExifのTAG>)
//
//                    // 画像の高さ
//                    var imageLength = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)
//                    // 画像の横幅
//                    var imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
//                    // 画像のビットの深さ
//                    var bitsPerSample = exifInterface.getAttribute(ExifInterface.TAG_BITS_PER_SAMPLE)
//                    // 圧縮の種類
//                    var compression = exifInterface.getAttribute(ExifInterface.TAG_COMPRESSION)
//                    // 画像タイトル
//                    var imageDescription = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION)
//                    // 画像方向
//                    var imageOrientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)
//                    // メーカ名
//                    var maker = exifInterface.getAttribute(ExifInterface.TAG_MAKE)
//                    //モデル名
//                    var model = exifInterface.getAttribute(ExifInterface.TAG_MODEL)
//                    // ロケーション
//                    var stripOffsets = exifInterface.getAttribute(ExifInterface.TAG_STRIP_OFFSETS)
//                    // GPSタグのバージョン
//                    var gpsVersionID = exifInterface.getAttribute(ExifInterface.TAG_GPS_VERSION_ID)
//                    // 経度
//                    var gpsLatitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
//                    // 緯度
//                    var gpsLongitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
//                    // 原画像データの生成日時
//                    var dateTimeOriginal = exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
//                    // 更新日時
//                    var changeDateAndTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)
//
//                    // セット -> exifInterface.setAttribute(ExifInterface.<TAG>, <value>)
//                    // セーブ -> exifInterface.saveAttributes()
//
//                    try {
//                        val database = dbHelper.writableDatabase
//                        val values = ContentValues()
//                        values.put("photo_id", a)
//                        values.put("imageName", image.imageName)
//                        values.put("imageLength", imageLength)
//                        values.put("imageWidth", imageWidth)
//                        values.put("bitsPerSample", bitsPerSample)
//                        values.put("compression", compression)
//                        values.put("imageDescription", imageDescription)
//                        values.put("imageOrientation", imageOrientation)
//                        values.put("maker", maker)
//                        values.put("model", model)
//                        values.put("stripOffsets", stripOffsets)
//                        values.put("gpsVersionID", gpsVersionID)
//                        values.put("gpsLatitude", gpsLatitude)
//                        values.put("gpsLongitude", gpsLongitude)
//                        values.put("dateTimeOriginal", dateTimeOriginal)
//                        values.put("changeDateAndTime", changeDateAndTime)
//                        database.insertOrThrow("Meta", null, values)
//                    }
//                    catch (e: SQLiteConstraintException){
//
//                    }
//                } catch (e: IOException) {
//                    e.stackTrace
//                    e.message?.let { Log.e("ExifActivity", it) }
//
//                }
//
//                images.add(image)
//                a += 1
//            } while (cursor.moveToNext())
//            cursor.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return images
//    }

}