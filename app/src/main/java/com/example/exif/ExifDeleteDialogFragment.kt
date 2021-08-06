package com.example.exif

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.io.File
import java.io.IOException


class ExifDeleteDialogFragment(
    private val message: String,
    private val okLabel: String,
    private val okSelected: () -> Unit,
    private val cancelLabel: String,
    private val cancelSelected: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(message)
        builder.setPositiveButton(okLabel) { dialog, which ->
            okSelected()
        }
        builder.setNegativeButton(cancelLabel) { dialog, which ->
            cancelSelected()
        }
        return builder.create()
    }


    // 一括削除ボタンを押したときの動作
    @RequiresApi(Build.VERSION_CODES.N)
    private fun allDeleteExif() {

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


        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
        // Exif用imagePath
        val f: File = File(imagePath)
        val uri = Uri.fromFile(f)

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

                // nullをセット
                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_LENGTH,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_WIDTH,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_Y_RESOLUTION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_X_RESOLUTION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_BITS_PER_SAMPLE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_COMPRESSION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ORIENTATION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_DESCRIPTION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ARTIST,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_MAKE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_MODEL,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_APERTURE_VALUE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_EXPOSURE_TIME,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ISO_SPEED_RATINGS,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_EXPOSURE_BIAS_VALUE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_F_NUMBER,
                    null
                )


                exifInterface.setAttribute(
                    ExifInterface.TAG_SHUTTER_SPEED_VALUE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_FOCAL_LENGTH,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_METERING_MODE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_FLASH,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_STRIP_OFFSETS,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_VERSION_ID,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_LATITUDE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_LONGITUDE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_ALTITUDE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_DATETIME_ORIGINAL,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_DATETIME,
                    null
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

            // nullをセット
            values.putNull("image_length")
            values.putNull("image_width")
            values.putNull("y_resolution")
            values.putNull("x_resolution")
            values.putNull("bits_per_sample")
            values.putNull("compression")
            values.putNull("image_orientation")
            values.putNull("image_description")
            values.putNull("artist")
            values.putNull("maker")
            values.putNull("model")
            values.putNull("aperture")
            values.putNull("exposure_time")
            values.putNull("iso_speed")
            values.putNull("exposure_bias")
            values.putNull("f_number")
            values.putNull("shutter_speed")
            values.putNull("focal_length")
            values.putNull("metering_mode")
            values.putNull("flash")
            values.putNull("strip_offsets")
            values.putNull("gps_version_id")
            values.putNull("gps_latitude")
            values.putNull("gps_longitude")
            values.putNull("gps_altitude")
            values.putNull("date_time_original")
            values.putNull("change_date_and_time")

            // 一括でMetaテーブルをアップデート
            database.update("Meta", values, "photo_id=$photoID", null)


            // トースト表示
            val text = "削除しました。"
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(context, text, duration).show()

        } catch (exception: Exception) {
            Log.e("deleteData", exception.toString())
        }

    }

}