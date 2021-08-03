package com.example.exif

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteConstraintException
import android.media.ExifInterface
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exif.databinding.FragmentPhotoBinding
import com.example.exif.model.Image
import com.example.exif.model.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_photo.*
import java.io.IOException

class PhotoFragment : Fragment() {

    // バインディング
    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    var a: Int = 1
    // var b: Int = 0

    //フィールドの記載
    private var imageRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var allPictures: ArrayList<Image>? = null


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)

        val navController = findNavController(nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        // リサイクルビューイメージのId定義
        imageRecycler = view?.findViewById(R.id.image_recycler)
        progressBar = view?.findViewById(R.id.recycler_progress)
        // リサイクルビューのグリットレイアウトで表示されている画像の制御、spanCountは4列で画像を並べてる意味
        imageRecycler?.layoutManager = GridLayoutManager(context, 4)
        // これで表示画像の大きさを均等になるよう修正を加えている。falseにしたら大変な事になる。
        imageRecycler?.setHasFixedSize(true)


        if (this.activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            this.activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101
                )
            }
        }

        allPictures = ArrayList()

        if (allPictures!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            //画像取得の際のプログレスバーの不可視設定かつimageRecyclerに
            // allPicturesの画像配列をセット。
            allPictures = getAllImages()
            //Adapterをリサイクラーにセットする
            imageRecycler?.adapter = PhotoAdapter(this, allPictures!!)
            progressBar?.visibility = View.GONE
        }

        return binding.root
    }

    //データベース接続
    val dbHelper = SampleDBHelper(this, "SampleDB", null, 1)

    //外部ストレージからすべての画像を取得するメソッドの設定
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor =
            this.activity?.contentResolver?.query(allImageUri, projection, null, null, null)

        try {
            cursor!!.moveToFirst()
            do {
                val image = Image()
                image.imageid = a.toString()
                image.imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                image.imageSentence1 = ""
                image.imageSentence2 = ""
                image.imageSentence3 = ""
                try {
                    val database = dbHelper.writableDatabase
                    val values = ContentValues()
                    values.put("id", a)
                    values.put("path", image.imagePath)
                    values.put("name", image.imageName)
                    values.put("sentence1", image.imageSentence1)
                    values.put("sentence2", image.imageSentence2)
                    values.put("sentence3", image.imageSentence3)
                    database.insertOrThrow("Photo", null, values)
                } catch (e: SQLiteConstraintException) {

                }

                // Exif取得
                try {
                    // 取得したい画像のURL
                    // ExifInterface exifInterface = new ExifInterface(imagePath);
                    // ↓は↑のコードをkotlinに直した
                    var exifInterface: ExifInterface? = imagePath?.let { ExifInterface(it) }

                    // 画像の高さ
                    var imageLength = exifInterface?.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)
                    // 画像の横幅
                    var imageWidth = exifInterface?.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
                    // 画像のビットの深さ
                    var bitsPerSample =
                        exifInterface?.getAttribute(ExifInterface.TAG_BITS_PER_SAMPLE)
                    // 圧縮の種類
                    var compression = exifInterface?.getAttribute(ExifInterface.TAG_COMPRESSION)
                    // 画像タイトル
                    var imageDescription =
                        exifInterface?.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION)
                    // 画像方向
                    var imageOrientation =
                        exifInterface?.getAttribute(ExifInterface.TAG_ORIENTATION)
                    // メーカ名
                    var maker = exifInterface?.getAttribute(ExifInterface.TAG_MAKE)
                    //モデル名
                    var model = exifInterface?.getAttribute(ExifInterface.TAG_MODEL)
                    // ロケーション
                    var stripOffsets = exifInterface?.getAttribute(ExifInterface.TAG_STRIP_OFFSETS)
                    // GPSタグのバージョン
                    var gpsVersionID = exifInterface?.getAttribute(ExifInterface.TAG_GPS_VERSION_ID)
                    // 経度
                    var gpsLatitude = exifInterface?.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
                    // 緯度
                    var gpsLongitude = exifInterface?.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
                    // 原画像データの生成日時
                    var dateTimeOriginal =
                        exifInterface?.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
                    // 更新日時
                    var changeDateAndTime = exifInterface?.getAttribute(ExifInterface.TAG_DATETIME)

                    try {
                        val database = dbHelper.writableDatabase
                        val values = ContentValues()
                        values.put("photo_id", a)
                        values.put("imageName", image.imageName)
                        values.put("imageLength", imageLength)
                        values.put("imageWidth", imageWidth)
                        values.put("bitsPerSample", bitsPerSample)
                        values.put("compression", compression)
                        values.put("imageDescription", imageDescription)
                        values.put("imageOrientation", imageOrientation)
                        values.put("maker", maker)
                        values.put("model", model)
                        values.put("stripOffsets", stripOffsets)
                        values.put("gpsVersionID", gpsVersionID)
                        values.put("gpsLatitude", gpsLatitude)
                        values.put("gpsLongitude", gpsLongitude)
                        values.put("dateTimeOriginal", dateTimeOriginal)
                        values.put("changeDateAndTime", changeDateAndTime)
                        database.insertOrThrow("Meta", null, values)
                    } catch (e: SQLiteConstraintException) {

                    }
                } catch (e: IOException) {
                    e.stackTrace
                    e.message?.let { Log.e("ExifActivity", it) }

                }

                images.add(image)
                a += 1
            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}