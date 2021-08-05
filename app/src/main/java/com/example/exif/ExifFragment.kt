package com.example.exif

import android.content.ContentValues
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentExifBinding
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*


class ExifFragment : Fragment() {

    // バインディング
    private var _binding: FragmentExifBinding? = null
    private val binding get() = _binding!!

    var arrayListPhotoId: ArrayList<String> = arrayListOf()
    var arrayListImageName: ArrayList<String> = arrayListOf()
    var arrayListImageLength: ArrayList<String> = arrayListOf()
    var arrayListImageWidth: ArrayList<String> = arrayListOf()
    var arrayListBitsPerSample: ArrayList<String> = arrayListOf()
    var arrayListCompressions: ArrayList<String> = arrayListOf()
    var arrayListImageDescription: ArrayList<String> = arrayListOf()
    var arrayListImageOrientation: ArrayList<String> = arrayListOf()
    var arrayListMaker: ArrayList<String> = arrayListOf()
    var arrayListModel: ArrayList<String> = arrayListOf()
    var arrayListStripOffsets: ArrayList<String> = arrayListOf()
    var arrayListGpsVersionID: ArrayList<String> = arrayListOf()
    var arrayListGpsLatitude: ArrayList<String> = arrayListOf()
    var arrayListGpsLongitude: ArrayList<String> = arrayListOf()
    var arrayListDateTimeOriginal: ArrayList<String> = arrayListOf()
    var arrayListChangeDateAndTime: ArrayList<String> = arrayListOf()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExifBinding.inflate(inflater, container, false)

        // Exif取得
        val f: File = File(imagePath)
        val uri = Uri.fromFile(f)
        var `in`: InputStream? = null


        //データベース接続
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        val sql =
            "SELECT photo_id, image_name, image_length, image_width, bits_per_sample, compression, image_description, image_orientation, maker, model, strip_offsets, gps_version_id, gps_latitude, gps_longitude, date_time_original, change_date_and_time FROM Meta WHERE photo_id = $photoID"
        val cursor = databaseR.rawQuery(sql, null)

        if (cursor.count > 0) {
            cursor.moveToFirst()

            while (!cursor.isAfterLast) {

                arrayListPhotoId.add(cursor.getString(0))
                arrayListImageName.add(cursor.getString(1))
                arrayListImageLength.add(cursor.getString(2))
                arrayListImageWidth.add(cursor.getString(3))
                arrayListBitsPerSample.add(cursor.getString(4))
                arrayListCompressions.add(cursor.getString(5))
                arrayListImageDescription.add(cursor.getString(6))
                arrayListImageOrientation.add(cursor.getString(7))
                arrayListMaker.add(cursor.getString(8))
                arrayListModel.add(cursor.getString(9))
                arrayListStripOffsets.add(cursor.getString(10))
                arrayListGpsVersionID.add(cursor.getString(11))
                arrayListGpsLatitude.add(cursor.getString(12))
                arrayListGpsLongitude.add(cursor.getString(13))
                arrayListDateTimeOriginal.add(cursor.getString(14))
                arrayListChangeDateAndTime.add(cursor.getString(15))

                cursor.moveToNext()
            }
        }


        // 画像の高さ
        var imageLength: String? = null
        // 画像の横幅
        var imageWidth: String? = null
        // 画像のビットの深さ
        var bitsPerSample: String? = null
        // 圧縮の種類
        var compression: String? = null
        // 画像タイトル
        var imageDescription: String? = null
        // 画像方向
        var imageOrientation: String? = null
        // メーカ名
        var maker: String? = null
        //モデル名
        var model: String? = null
        // ロケーション
        var stripOffsets: String? = null
        // GPSタグのバージョン
        var gpsVersionID: String? = null
        // 経度
        var gpsLatitude: String? = null
        // 緯度
        var gpsLongitude: String? = null
        // 原画像データの生成日時
        var dateTimeOriginal: String? = null
        // 更新日時
        var changeDateAndTime: String? = null

        try {
            // 画像の高さ
            imageLength = arrayListImageLength[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 画像の横幅
            imageWidth = arrayListImageWidth[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 画像のビットの深さ
            bitsPerSample = arrayListBitsPerSample[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 圧縮の種類
            compression = arrayListCompressions[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 画像タイトル
            imageDescription = arrayListImageDescription[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 画像方向
            imageOrientation = arrayListImageOrientation[0]
        } catch (e: NullPointerException) {

        }
        try {
            // メーカ名
            maker = arrayListMaker[0]
        } catch (e: NullPointerException) {

        }
        try {
            //モデル名
            model = arrayListModel[0]
        } catch (e: NullPointerException) {

        }
        try {
            // ロケーション
            stripOffsets = arrayListStripOffsets[0]
        } catch (e: NullPointerException) {

        }
        try {
            // GPSタグのバージョン
            gpsVersionID = arrayListGpsVersionID[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 経度
            gpsLatitude = arrayListGpsLatitude[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 緯度
            gpsLongitude = arrayListGpsLongitude[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 原画像データの生成日時
            dateTimeOriginal = arrayListDateTimeOriginal[0]
        } catch (e: NullPointerException) {

        }
        try {
            // 更新日時
            changeDateAndTime = arrayListChangeDateAndTime[0]
        } catch (e: NullPointerException) {

        }

        // データセット
        binding.imageLength.setText(imageLength)
        binding.imageWidth.setText(imageWidth)
        binding.bitsPerSample.setText(bitsPerSample)
        binding.compression.setText(compression)
        binding.imageDescription.setText(imageDescription)
        binding.imageOrientation.setText(imageOrientation)
        binding.maker.setText(maker)
        binding.model.setText(model)
        binding.stripOffsets.setText(stripOffsets)
        binding.gpsVersionID.setText(gpsVersionID)
        binding.gpsLatitude.setText(gpsLatitude)
        binding.gpsLongitude.setText(gpsLongitude)
        binding.dateTimeOriginal.setText(dateTimeOriginal)
        binding.changeDateAndTime.setText(changeDateAndTime)


        // 保存ボタンを押したときの動作
        binding.exifSave.setOnClickListener {
            updateExif()
        }

        return binding.root
    }


    // 保存ボタンを押したときの動作
    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateExif() {

        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
        // Exif用imagePath
        val f: File = File(imagePath)
        val uri = Uri.fromFile(f)

        // 各ExifのEditTextを変数に取得
        val imageLength = view?.findViewById<EditText>(R.id.imageLength)
        val imageWidth = view?.findViewById<EditText>(R.id.imageWidth)
        val bitsPerSample = view?.findViewById<EditText>(R.id.bitsPerSample)
        val compression = view?.findViewById<EditText>(R.id.compression)
        val imageDescription = view?.findViewById<EditText>(R.id.imageDescription)
        val imageOrientation = view?.findViewById<EditText>(R.id.imageOrientation)
        val maker = view?.findViewById<EditText>(R.id.maker)
        val model = view?.findViewById<EditText>(R.id.model)
        val stripOffsets = view?.findViewById<EditText>(R.id.stripOffsets)
        val gpsVersionID = view?.findViewById<EditText>(R.id.gpsVersionID)
        val gpsLatitude = view?.findViewById<EditText>(R.id.gpsLatitude)
        val gpsLongitude = view?.findViewById<EditText>(R.id.gpsLongitude)
        val dateTimeOriginal = view?.findViewById<EditText>(R.id.dateTimeOriginal)
        val changeDateAndTime = view?.findViewById<EditText>(R.id.changeDateAndTime)

        // Exif直接編集処理
        try {
            activity?.contentResolver?.openInputStream(uri)?.use {
                val exifInterface = ExifInterface(
                    context?.contentResolver?.openFileDescriptor(
                        uri,
                        "rw",
                        null
                    )!!.fileDescriptor
                )

                // EditTextの値をExifにセット
                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_LENGTH,
                    imageLength.toString()
                )
                exifInterface.setAttribute(ExifInterface.TAG_IMAGE_WIDTH, imageWidth.toString())
                exifInterface.setAttribute(
                    ExifInterface.TAG_BITS_PER_SAMPLE,
                    bitsPerSample.toString()
                )
                exifInterface.setAttribute(
                    ExifInterface.TAG_COMPRESSION,
                    compression.toString()
                )
                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_DESCRIPTION,
                    imageDescription.toString()
                )
                exifInterface.setAttribute(
                    ExifInterface.TAG_ORIENTATION,
                    imageOrientation.toString()
                )
                exifInterface.setAttribute(ExifInterface.TAG_MAKE, maker.toString())
                exifInterface.setAttribute(ExifInterface.TAG_MODEL, model.toString())
                exifInterface.setAttribute(
                    ExifInterface.TAG_STRIP_OFFSETS,
                    stripOffsets.toString()
                )
                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_VERSION_ID,
                    gpsVersionID.toString()
                )
                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_LATITUDE,
                    gpsLatitude.toString()
                )
                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_LONGITUDE,
                    gpsLongitude.toString()
                )
                exifInterface.setAttribute(
                    ExifInterface.TAG_DATETIME_ORIGINAL,
                    dateTimeOriginal.toString()
                )
                exifInterface.setAttribute(
                    ExifInterface.TAG_DATETIME,
                    changeDateAndTime.toString()
                )

                // Exifを更新
                exifInterface.saveAttributes()
            }

        } catch (e: IOException) {
            e.stackTrace
            e.message?.let { Log.e("ExifActivity", it) }
        }

        // データベース更新処理
        try {
            // 前処理
            val database = dbHelper.writableDatabase
            val values = ContentValues()

            // 各カラムの値をセット
            values.put("image_length", imageLength?.text.toString())
            values.put("image_width", imageWidth?.text.toString())
            values.put("bits_per_sample", bitsPerSample?.text.toString())
            values.put("compression", compression?.text.toString())
            values.put("image_description", imageDescription?.text.toString())
            values.put("image_orientation", imageOrientation?.text.toString())
            values.put("maker", maker?.text.toString())
            values.put("model", model?.text.toString())
            values.put("strip_offsets", stripOffsets?.text.toString())
            values.put("gps_version_id", gpsVersionID?.text.toString())
            values.put("gps_latitude", gpsLatitude?.text.toString())
            values.put("gps_longitude", gpsLongitude?.text.toString())
            values.put("date_time_original", dateTimeOriginal?.text.toString())
            values.put("change_date_and_time", changeDateAndTime?.text.toString())

            // 一括でMetaテーブルをアップデート
            database.update("Meta", values, "photo_id=$photoID", null)

            // トースト表示
            val text = "保存しました。"
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(context, text, duration).show()

        } catch (exception: Exception) {
            Log.e("updateData", exception.toString())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}