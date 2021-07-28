package com.example.exif

import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentExifBinding
import java.io.File
import java.io.IOException
import java.io.InputStream


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

        // Exif取得
        val f: File = File(imagePath)
        val uri = Uri.fromFile(f)
        var `in`: InputStream? = null

        try {
            `in` = activity?.contentResolver?.openInputStream(uri)
            var exifInterface = ExifInterface(`in`!!)

            // Exifの各値をここにセット
            // <変数> = exifInterface.getAttribute(ExifInterface.<ExifのTAG>)

            // 画像の高さ
            var imageLength = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)
            // 画像の横幅
            var imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
            // 画像のビットの深さ
            var bitsPerSample = exifInterface.getAttribute(ExifInterface.TAG_BITS_PER_SAMPLE)
            // 圧縮の種類
            var compression = exifInterface.getAttribute(ExifInterface.TAG_COMPRESSION)
            // 画像タイトル
            var imageDescription = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION)
            // 画像方向
            var imageOrientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)
            // メーカ名
            var maker = exifInterface.getAttribute(ExifInterface.TAG_MAKE)
            //モデル名
            var model = exifInterface.getAttribute(ExifInterface.TAG_MODEL)
            // ロケーション
            var stripOffsets = exifInterface.getAttribute(ExifInterface.TAG_STRIP_OFFSETS)
            // GPSタグのバージョン
            var gpsVersionID = exifInterface.getAttribute(ExifInterface.TAG_GPS_VERSION_ID)
            // 経度
            var gpsLatitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
            // 緯度
            var gpsLongitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
            // 原画像データの生成日時
            var dateTimeOriginal = exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
            // 更新日時
            var dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)

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
            binding.changeDateAndTime.setText(dateTime)

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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}