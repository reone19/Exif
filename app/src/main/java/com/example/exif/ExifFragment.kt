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
import java.util.*


class ExifFragment : Fragment() {

    // バインディング
    private var _binding: FragmentExifBinding? = null
    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExifBinding.inflate(inflater, container, false)

        val arrayListPhotoId: ArrayList<String> = arrayListOf()
        val arrayListImageName: ArrayList<String> = arrayListOf()
        val arrayListImageLength: ArrayList<String> = arrayListOf()
        val arrayListImageWidth: ArrayList<String> = arrayListOf()
        val arrayListYResolution: ArrayList<String> = arrayListOf()
        val arrayListXResolution: ArrayList<String> = arrayListOf()
        val arrayListBitsPerSample: ArrayList<String> = arrayListOf()
        val arrayListCompressions: ArrayList<String> = arrayListOf()
        val arrayListImageOrientation: ArrayList<String> = arrayListOf()
        val arrayListImageDescription: ArrayList<String> = arrayListOf()
        val arrayListArtist: ArrayList<String> = arrayListOf()
        val arrayListMaker: ArrayList<String> = arrayListOf()
        val arrayListModel: ArrayList<String> = arrayListOf()
        val arrayListAperture: ArrayList<String> = arrayListOf()
        val arrayListExposureTime: ArrayList<String> = arrayListOf()
        val arrayListIsoSpeed: ArrayList<String> = arrayListOf()
        val arrayListExposureBias: ArrayList<String> = arrayListOf()
        val arrayListFNumber: ArrayList<String> = arrayListOf()
        val arrayListShutterSpeed: ArrayList<String> = arrayListOf()
        val arrayListFocalLength: ArrayList<String> = arrayListOf()
        val arrayListMeteringMode: ArrayList<String> = arrayListOf()
        val arrayListFlash: ArrayList<String> = arrayListOf()
        val arrayListStripOffsets: ArrayList<String> = arrayListOf()
        val arrayListGpsVersionID: ArrayList<String> = arrayListOf()
        val arrayListGpsLatitude: ArrayList<String> = arrayListOf()
        val arrayListGpsLongitude: ArrayList<String> = arrayListOf()
        val arrayListGpsAltitude: ArrayList<String> = arrayListOf()
        val arrayListDateTimeOriginal: ArrayList<String> = arrayListOf()
        val arrayListChangeDateAndTime: ArrayList<String> = arrayListOf()

        //データベース接続
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        val sql =
            "SELECT photo_id, image_name, image_length, image_width, y_resolution, x_resolution, bits_per_sample, compression, image_orientation, image_description, artist, maker, model, aperture, exposure_time, iso_speed, exposure_bias, f_number, shutter_speed, focal_length, metering_mode, flash, strip_offsets, gps_version_id, gps_latitude, gps_longitude, gps_altitude, date_time_original, change_date_and_time FROM Meta WHERE photo_id = $photoID"
        val cursor = databaseR.rawQuery(sql, null)

        if (cursor.count > 0) {
            cursor.moveToFirst()

            while (!cursor.isAfterLast) {

                arrayListPhotoId.add(cursor.getString(0))
                arrayListImageName.add(cursor.getString(1))
                arrayListImageLength.add(cursor.getString(2))
                arrayListImageWidth.add(cursor.getString(3))
                arrayListYResolution.add(cursor.getString(4))
                arrayListXResolution.add(cursor.getString(5))
                arrayListBitsPerSample.add(cursor.getString(6))
                arrayListCompressions.add(cursor.getString(7))
                arrayListImageOrientation.add(cursor.getString(8))
                arrayListImageDescription.add(cursor.getString(9))
                arrayListArtist.add(cursor.getString(10))
                arrayListMaker.add(cursor.getString(11))
                arrayListModel.add(cursor.getString(12))
                arrayListAperture.add(cursor.getString(13))
                arrayListExposureTime.add(cursor.getString(14))
                arrayListIsoSpeed.add(cursor.getString(15))
                arrayListExposureBias.add(cursor.getString(16))
                arrayListFNumber.add(cursor.getString(17))
                arrayListShutterSpeed.add(cursor.getString(18))
                arrayListFocalLength.add(cursor.getString(19))
                arrayListMeteringMode.add(cursor.getString(20))
                arrayListFlash.add(cursor.getString(21))
                arrayListStripOffsets.add(cursor.getString(22))
                arrayListGpsVersionID.add(cursor.getString(23))
                arrayListGpsLatitude.add(cursor.getString(24))
                arrayListGpsLongitude.add(cursor.getString(25))
                arrayListGpsAltitude.add(cursor.getString(26))
                arrayListDateTimeOriginal.add(cursor.getString(27))
                arrayListChangeDateAndTime.add(cursor.getString(28))

                cursor.moveToNext()
            }
        }


        // 画像の高さ
        var imageLength: String? = null
        // 画像の横幅
        var imageWidth: String? = null
        // 画像の高さの解像度
        var yResolution: String? = null
        // 画像の横幅の解像度
        var xResolution: String? = null
        // 画像のビットの深さ
        var bitsPerSample: String? = null
        // 圧縮の種類
        var compression: String? = null
        // 画像方向
        var imageOrientation: String? = null
        // 画像タイトル
        var imageDescription: String? = null
        // 作者名
        var artist: String? = null
        // メーカ名
        var maker: String? = null
        //モデル名
        var model: String? = null
        // 絞り値
        var aperture: String? = null
        // 露出時間
        var exposureTime: String? = null
        // ISO値
        var isoSpeed: String? = null
        // 露出補正時間
        var exposureBias: String? = null
        // F値
        var fNumber: String? = null
        // シャッタースピード
        var shutterSpeed: String? = null
        // 焦点距離
        var focalLength: String? = null
        // 測光モード
        var meteringMode: String? = null
        // フラッシュ
        var flash: String? = null
        // ロケーション
        var stripOffsets: String? = null
        // GPSタグのバージョン
        var gpsVersionID: String? = null
        // 経度
        var gpsLatitude: String? = null
        // 緯度
        var gpsLongitude: String? = null
        // 高度
        var gpsAltitude: String? = null
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
            // 画像の高さの解像度
            yResolution = arrayListImageWidth[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 画像の横幅の解像度
            xResolution = arrayListImageWidth[0]
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
            // 画像方向
            imageOrientation = arrayListImageOrientation[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 画像タイトル
            imageDescription = arrayListImageDescription[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 作者名
            artist = arrayListImageDescription[0]
        } catch (e: NullPointerException) {
        }

        try {
            // メーカ名
            maker = arrayListMaker[0]
        } catch (e: NullPointerException) {
        }

        try {
            // モデル名
            model = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 絞り値
            aperture = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 露出時間
            exposureTime = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // ISO値
            isoSpeed = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 露出補正時間
            exposureBias = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // F値
            fNumber = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // シャッタースピード
            shutterSpeed = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 焦点距離
            focalLength = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 測光モード
            meteringMode = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // フラッシュ
            flash = arrayListModel[0]
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
            // 高度
            gpsAltitude = arrayListGpsLongitude[0]
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
        binding.yResolution.setText(yResolution)
        binding.xResolution.setText(xResolution)
        binding.bitsPerSample.setText(bitsPerSample)
        binding.compression.setText(compression)
        binding.imageOrientation.setText(imageOrientation)
        binding.imageDescription.setText(imageDescription)
        binding.artist.setText(artist)
        binding.maker.setText(maker)
        binding.model.setText(model)
        binding.aperture.setText(aperture)
        binding.exposureTime.setText(exposureTime)
        binding.isoSpeed.setText(isoSpeed)
        binding.exposureBias.setText(exposureBias)
        binding.fNumber.setText(fNumber)
        binding.shutterSpeed.setText(shutterSpeed)
        binding.focalLength.setText(focalLength)
        binding.meteringMode.setText(meteringMode)
        binding.flash.setText(flash)
        binding.stripOffsets.setText(stripOffsets)
        binding.gpsVersionID.setText(gpsVersionID)
        binding.gpsLatitude.setText(gpsLatitude)
        binding.gpsLongitude.setText(gpsLongitude)
        binding.gpsAltitude.setText(gpsAltitude)
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
        val yResolution = view?.findViewById<EditText>(R.id.yResolution)
        val xResolution = view?.findViewById<EditText>(R.id.xResolution)
        val bitsPerSample = view?.findViewById<EditText>(R.id.bitsPerSample)
        val compression = view?.findViewById<EditText>(R.id.compression)
        val imageOrientation = view?.findViewById<EditText>(R.id.imageOrientation)
        val imageDescription = view?.findViewById<EditText>(R.id.imageDescription)
        val artist = view?.findViewById<EditText>(R.id.artist)
        val maker = view?.findViewById<EditText>(R.id.maker)
        val model = view?.findViewById<EditText>(R.id.model)
        val aperture = view?.findViewById<EditText>(R.id.aperture)
        val exposureTime = view?.findViewById<EditText>(R.id.exposureTime)
        val isoSpeed = view?.findViewById<EditText>(R.id.isoSpeed)
        val exposureBias = view?.findViewById<EditText>(R.id.exposureBias)
        val fNumber = view?.findViewById<EditText>(R.id.fNumber)
        val shutterSpeed = view?.findViewById<EditText>(R.id.shutterSpeed)
        val focalLength = view?.findViewById<EditText>(R.id.focalLength)
        val meteringMode = view?.findViewById<EditText>(R.id.meteringMode)
        val flash = view?.findViewById<EditText>(R.id.flash)
        val stripOffsets = view?.findViewById<EditText>(R.id.stripOffsets)
        val gpsVersionID = view?.findViewById<EditText>(R.id.gpsVersionID)
        val gpsLatitude = view?.findViewById<EditText>(R.id.gpsLatitude)
        val gpsLongitude = view?.findViewById<EditText>(R.id.gpsLongitude)
        val gpsAltitude = view?.findViewById<EditText>(R.id.gpsAltitude)
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

                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_WIDTH,
                    imageWidth.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_Y_RESOLUTION,
                    yResolution.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_X_RESOLUTION,
                    xResolution.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_BITS_PER_SAMPLE,
                    bitsPerSample.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_COMPRESSION,
                    compression.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ORIENTATION,
                    imageOrientation.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_DESCRIPTION,
                    imageDescription.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ARTIST,
                    artist.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_MAKE,
                    maker.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_MODEL,
                    model.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_APERTURE_VALUE,
                    aperture .toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_EXPOSURE_TIME,
                    exposureTime.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ISO_SPEED_RATINGS,
                    isoSpeed.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_EXPOSURE_BIAS_VALUE,
                    exposureBias.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_F_NUMBER,
                    fNumber.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_SHUTTER_SPEED_VALUE,
                    shutterSpeed.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_FOCAL_LENGTH,
                    focalLength.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_METERING_MODE,
                    meteringMode.toString()
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_FLASH,
                    flash.toString()
                )

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
                    ExifInterface.TAG_GPS_ALTITUDE,
                    gpsAltitude.toString()
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
            values.put("y_resolution", yResolution?.text.toString())
            values.put("x_resolution", xResolution?.text.toString())
            values.put("bits_per_sample", bitsPerSample?.text.toString())
            values.put("compression", compression?.text.toString())
            values.put("image_orientation", imageOrientation?.text.toString())
            values.put("image_description", imageDescription?.text.toString())
            values.put("artist", artist?.text.toString())
            values.put("maker", maker?.text.toString())
            values.put("model", model?.text.toString())
            values.put("strip_offsets", stripOffsets?.text.toString())
            values.put("aperture", aperture?.text.toString())
            values.put("exposure_time", exposureTime?.text.toString())
            values.put("iso_speed", isoSpeed?.text.toString())
            values.put("exposure_bias", exposureBias?.text.toString())
            values.put("f_number", fNumber?.text.toString())
            values.put("shutter_speed", shutterSpeed?.text.toString())
            values.put("focal_length", focalLength?.text.toString())
            values.put("metering_mode", meteringMode?.text.toString())
            values.put("flash", flash?.text.toString())
            values.put("gps_version_id", gpsVersionID?.text.toString())
            values.put("gps_latitude", gpsLatitude?.text.toString())
            values.put("gps_longitude", gpsLongitude?.text.toString())
            values.put("gps_altitude", gpsAltitude?.text.toString())
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