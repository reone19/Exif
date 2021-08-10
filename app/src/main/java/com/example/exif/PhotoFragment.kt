package com.example.exif

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteConstraintException
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exif.databinding.FragmentPhotoBinding
import com.example.exif.model.Image
import com.example.exif.model.PhotoAdapter
import java.io.File
import java.io.IOException
import java.io.InputStream

// 画像のパスを配列にすべて保存
var allImagePath: MutableList<String> = emptyList<String>().toMutableList()
// var allImageName: MutableList<String> = emptyList<String>().toMutableList()
// var allImageSentence1: MutableList<String> = emptyList<String>().toMutableList()
// var allImageSentence2: MutableList<String> = emptyList<String>().toMutableList()
// var allImageSentence3: MutableList<String> = emptyList<String>().toMutableList()


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
        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        // リサイクルビューイメージのId定義
        imageRecycler = view.findViewById(R.id.image_recycler)
        progressBar = view.findViewById(R.id.recycler_progress)
        // リサイクルビューのグリットレイアウトで表示されている画像の制御、spanCountは4列で画像を並べてる意味
        imageRecycler?.layoutManager = GridLayoutManager(requireContext(), 4)
        // これで表示画像の大きさを均等になるよう修正を加えている。falseにしたら大変な事になる。
        imageRecycler?.setHasFixedSize(true)


        // API30以上の時だけ追加の権限の確認が必要（Exifに使う）
        if (Build.VERSION.SDK_INT >= 30) {

            // すべてのファイルを管理できる権限を付与（Exifの編集に必要）
            // 許可しない限りアプリが使用できない
            if (Environment.isExternalStorageManager()) {
                // todo when permission is granted
            } else {
                // request for the permission
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri: Uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }

        }


        // READ権限を付与する確認ポップアップ
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

        // WRITE権限を付与する確認ポップアップ
        if (this.activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            this.activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 101
                )
            }
        }

        allPictures = ArrayList()

        if (allPictures!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            // 画像取得の際のプログレスバーの不可視設定かつimageRecyclerに
            // allPicturesの画像配列をセット。
            allPictures = getAllImages()
            // Adapterをリサイクラーにセットする
            imageRecycler?.adapter = PhotoAdapter(requireContext(), allPictures!!)
            progressBar?.visibility = View.GONE
        }

        return view
    }


    // 外部ストレージからすべての画像を取得するメソッドの設定
    @RequiresApi(Build.VERSION_CODES.N)
    fun getAllImages(): ArrayList<Image>? {

        // データベース接続
        val dbHelper = context?.let { SampleDBHelper(it, "SampleDB", null, 1) }

        val images = ArrayList<Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor =
            this.activity?.contentResolver?.query(allImageUri, projection, null, null, null)

        try {
            cursor!!.moveToFirst()
            do {
                var image = Image()
                image.imageId = a.toString()
                image.imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                image.imageSentence1 = null
                image.imageSentence2 = null
                image.imageSentence3 = null

                // allImagePathに配列で全ての府画像パスを取得
                // 画像横スライドに使う
                allImagePath.add(image.imagePath.toString())
                // allImageName.add(image.imageName.toString())
                // allImageSentence1.add(image.imageSentence1.toString())
                // allImageSentence2.add(image.imageSentence2.toString())
                // allImageSentence3.add(image.imageSentence3.toString())
                // Log.e("imageName", image.imageId.toString())

                try {
                    val database = dbHelper?.writableDatabase
                    val values = ContentValues()
                    values.put("id", a)
                    values.put("path", image.imagePath)
                    values.put("name", image.imageName)
                    values.put("sentence1", image.imageSentence1)
                    values.put("sentence2", image.imageSentence2)
                    values.put("sentence3", image.imageSentence3)
                    database?.insertOrThrow("Photo", null, values)
                } catch (e: SQLiteConstraintException) {

                }

                // Exif取得
                val f: File = File(image.imagePath)
                val uri = Uri.fromFile(f)
                var `in`: InputStream? = null

                try {
                    `in` = activity?.contentResolver?.openInputStream(uri)
                    val exifInterface = ExifInterface(`in`!!)

                    // 画像の高さ
                    val imageLength = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)
                    // 画像の横幅
                    val imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
                    // 画像の高さの解像度
                    val yResolution = exifInterface.getAttribute(ExifInterface.TAG_Y_RESOLUTION)
                    // 画像の横幅の解像度
                    val xResolution = exifInterface.getAttribute(ExifInterface.TAG_X_RESOLUTION)
                    // 画像のビットの深さ
                    val bitsPerSample =
                        exifInterface.getAttribute(ExifInterface.TAG_BITS_PER_SAMPLE)
                    // 圧縮の種類
                    val compression = exifInterface.getAttribute(ExifInterface.TAG_COMPRESSION)
                    // 画像方向
                    val imageOrientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)
                    // 画像タイトル
                    val imageDescription =
                        exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION)
                    // 作者名
                    val artist = exifInterface.getAttribute(ExifInterface.TAG_ARTIST)
                    // メーカ名
                    val maker = exifInterface.getAttribute(ExifInterface.TAG_MAKE)
                    //モデル名
                    val model = exifInterface.getAttribute(ExifInterface.TAG_MODEL)
                    // 絞り値
                    val aperture = exifInterface.getAttribute(ExifInterface.TAG_APERTURE_VALUE)
                    // 露出時間
                    val exposureTime = exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME)
                    // ISO値
                    val isoSpeed = exifInterface.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS)
                    // 露出補正時間
                    val exposureBias =
                        exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_BIAS_VALUE)
                    // F値
                    val fNumber = exifInterface.getAttribute(ExifInterface.TAG_F_NUMBER)
                    // シャッタースピード
                    val shutterSpeed =
                        exifInterface.getAttribute(ExifInterface.TAG_SHUTTER_SPEED_VALUE)
                    // 焦点距離
                    val focalLength = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)
                    // 測光モード
                    val meteringMode = exifInterface.getAttribute(ExifInterface.TAG_METERING_MODE)
                    // フラッシュ
                    val flash = exifInterface.getAttribute(ExifInterface.TAG_FLASH)
                    // ロケーション
                    val stripOffsets = exifInterface.getAttribute(ExifInterface.TAG_STRIP_OFFSETS)
                    // GPSタグのバージョン
                    val gpsVersionID = exifInterface.getAttribute(ExifInterface.TAG_GPS_VERSION_ID)
                    // 経度
                    val gpsLatitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
                    // 緯度
                    val gpsLongitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
                    // 高度
                    val gpsAltitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE)
                    // 原画像データの生成日時
                    val dateTimeOriginal =
                        exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
                    // 更新日時
                    val changeDateAndTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)


                    try {
                        val database = dbHelper?.writableDatabase
                        val values = ContentValues()

                        values.put("photo_id", a)
                        values.put("image_name", image.imageName)
                        values.put("image_length", imageLength)
                        values.put("image_width", imageWidth)
                        values.put("y_resolution", yResolution)
                        values.put("x_resolution", xResolution)
                        values.put("bits_per_sample", bitsPerSample)
                        values.put("compression", compression)
                        values.put("image_orientation", imageOrientation)
                        values.put("image_description", imageDescription)
                        values.put("artist", artist)
                        values.put("maker", maker)
                        values.put("model", model)
                        values.put("aperture", aperture)
                        values.put("exposure_time", exposureTime)
                        values.put("iso_speed", isoSpeed)
                        values.put("exposure_bias", exposureBias)
                        values.put("f_number", fNumber)
                        values.put("shutter_speed", shutterSpeed)
                        values.put("focal_length", focalLength)
                        values.put("metering_mode", meteringMode)
                        values.put("flash", flash)
                        values.put("strip_offsets", stripOffsets)
                        values.put("gps_version_id", gpsVersionID)
                        values.put("gps_latitude", gpsLatitude)
                        values.put("gps_longitude", gpsLongitude)
                        values.put("gps_altitude", gpsAltitude)
                        values.put("date_time_original", dateTimeOriginal)
                        values.put("change_date_and_time", changeDateAndTime)

                        database?.insertOrThrow("Meta", null, values)
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