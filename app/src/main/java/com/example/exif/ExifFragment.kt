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
            var imageLength = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)
            var imageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
            var imageOrientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)
            var maker = exifInterface.getAttribute(ExifInterface.TAG_MAKE)
            var model = exifInterface.getAttribute(ExifInterface.TAG_MODEL)
            var dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)

            // データセット
            binding.imageLength.setText(imageLength)
            binding.imageWidth.setText(imageWidth)
            binding.imageOrientation.setText(imageOrientation)
            binding.maker.setText(maker)
            binding.model.setText(model)
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