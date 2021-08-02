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
    var arrayListimageLength: ArrayList<String> = arrayListOf()
    var arrayListimageWidth: ArrayList<String> = arrayListOf()
    var arrayListbitsPerSample: ArrayList<String> = arrayListOf()
    var arrayListcompressions: ArrayList<String> = arrayListOf()
    var arrayListimageDescription: ArrayList<String> = arrayListOf()
    var arrayListimageOrientation: ArrayList<String> = arrayListOf()
    var arrayListmaker: ArrayList<String> = arrayListOf()
    var arrayListmodel: ArrayList<String> = arrayListOf()
    var arrayListstripOffsets: ArrayList<String> = arrayListOf()
    var arrayListgpsVersionID: ArrayList<String> = arrayListOf()
    var arrayListgpsLatitude: ArrayList<String> = arrayListOf()
    var arrayListgpsLongitude: ArrayList<String> = arrayListOf()
    var arrayListdateTimeOriginal: ArrayList<String> = arrayListOf()
    var arrayListchangeDateAndTime: ArrayList<String> = arrayListOf()


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

        try {
            `in` = activity?.contentResolver?.openInputStream(uri)
            var exifInterface = ExifInterface(`in`!!)

            // Exifの各値をここにセット
            // <変数> = exifInterface.getAttribute(ExifInterface.<ExifのTAG>)

            //データベース接続
            val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

            // データの取得処理
            val databaseR = dbHelper.readableDatabase
            val sql =
                "select photo_id, imageName, imageLength, imageWidth, bitsPerSample, compression, imageDescription, imageOrientation, maker, model, stripOffsets, gpsVersionID, gpsLatitude, gpsLongitude, dateTimeOriginal, changeDateAndTime from Meta where photo_id = " + photoID
            val cursor = databaseR.rawQuery(sql, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    arrayListPhotoId.add(cursor.getString(0))
                    arrayListImageName.add(cursor.getString(1))
                    arrayListimageLength.add(cursor.getString(2))
                    arrayListimageWidth.add(cursor.getString(3))
                    arrayListbitsPerSample.add(cursor.getString(4))
                    arrayListcompressions.add(cursor.getString(5))
                    arrayListimageDescription.add(cursor.getString(6))
                    arrayListimageOrientation.add(cursor.getString(7))
                    arrayListmaker.add(cursor.getString(8))
                    arrayListmodel.add(cursor.getString(9))
                    arrayListstripOffsets.add(cursor.getString(10))
                    arrayListgpsVersionID.add(cursor.getString(11))
                    arrayListgpsLatitude.add(cursor.getString(12))
                    arrayListgpsLongitude.add(cursor.getString(13))
                    arrayListdateTimeOriginal.add(cursor.getString(14))
                    arrayListchangeDateAndTime.add(cursor.getString(15))
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
                imageLength = arrayListimageLength.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 画像の横幅
                imageWidth = arrayListimageWidth.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 画像のビットの深さ
                bitsPerSample = arrayListbitsPerSample.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 圧縮の種類
                compression = arrayListcompressions.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 画像タイトル
                imageDescription = arrayListimageDescription.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 画像方向
                imageOrientation = arrayListimageOrientation.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // メーカ名
                maker = arrayListmaker.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                //モデル名
                model = arrayListmodel.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // ロケーション
                stripOffsets = arrayListstripOffsets.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // GPSタグのバージョン
                gpsVersionID = arrayListgpsVersionID.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 経度
                gpsLatitude = arrayListgpsLatitude.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 緯度
                gpsLongitude = arrayListgpsLongitude.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 原画像データの生成日時
                dateTimeOriginal = arrayListdateTimeOriginal.get(0)
            } catch (e: NullPointerException) {

            }
            try {
                // 更新日時
                changeDateAndTime = arrayListchangeDateAndTime.get(0)
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

            // セット -> exifInterface.setAttribute(ExifInterface.<TAG>, <value>)
            // セーブ -> exifInterface.saveAttributes()

        } catch (e: IOException) {
            e.stackTrace
            e.message?.let { Log.e("ExifActivity", it) }

        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (ignored: IOException) {
                }
            }
        }

        // 保存ボタンを押したときの動作
        binding.exifSave.setOnClickListener {
            updateExif()
        }

        return binding.root
    }


    // 保存ボタンを押したときの動作
    private fun updateExif() {
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

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

        // データベース更新処理
        try {
            // 前処理
            val database = dbHelper.writableDatabase
            val values = ContentValues()

            // 各カラムの値を更新
            values.put("imageLength", imageLength?.text.toString())
            values.put("imageWidth", imageWidth?.text.toString())
            values.put("bitsPerSample", bitsPerSample?.text.toString())
            values.put("compression", compression?.text.toString())
            values.put("imageDescription", imageDescription?.text.toString())
            values.put("imageOrientation", imageOrientation?.text.toString())
            values.put("maker", maker?.text.toString())
            values.put("model", model?.text.toString())
            values.put("stripOffsets", stripOffsets?.text.toString())
            values.put("gpsVersionID", gpsVersionID?.text.toString())
            values.put("gpsLatitude", gpsLatitude?.text.toString())
            values.put("gpsLongitude", gpsLongitude?.text.toString())
            values.put("dateTimeOriginal", dateTimeOriginal?.text.toString())
            values.put("changeDateAndTime", changeDateAndTime?.text.toString())

            // 一括でMetaテーブルをアップデート
            database.update("Meta", values, "photo_id=$photoID", null)

        } catch (exception: Exception) {
            Log.e("updateData", exception.toString())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}