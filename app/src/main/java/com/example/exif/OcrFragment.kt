package com.example.exif

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentOcrBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException


class OcrFragment : Fragment() {

    // バインディング
    private var _binding: FragmentOcrBinding? = null
    private val binding get() = _binding!!

    // OCR
    private var _ocr: OCR? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOcrBinding.inflate(inflater, container, false)

        _ocr = activity?.let { OCR(it.applicationContext) }

        // OCR
        val ocrString: String
        var bitmap: Bitmap? = null

        // URI取得
        val f: File = File(imagePath)
        val uri = Uri.fromFile(f)

        if (Build.VERSION.SDK_INT < 19) {
            try {
                val `in` = activity?.contentResolver?.openInputStream(uri!!)
                bitmap = BitmapFactory.decodeStream(`in`)
                try {
                    `in`?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        } else {
            try {
                val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(
                    uri!!, "r"
                )
                if (parcelFileDescriptor != null) {
                    val fileDescriptor = parcelFileDescriptor.fileDescriptor
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                    parcelFileDescriptor.close()
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        ocrString = if (bitmap != null) {
            activity?.applicationContext?.let { _ocr!!.getString(it, bitmap) }
                .toString()
        } else {
            "bitmap is null"
        }
        binding.ocrString.setText(ocrString)

        binding.btn.setOnClickListener {
            updateOcr()
        }

        return binding.root
    }
    private fun updateOcr() {
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

        val ocr = view?.findViewById<EditText>(R.id.ocrString)

        try {
            val database = dbHelper.writableDatabase
            val values = ContentValues()

            // 空文字のときはnullを挿入
            if (ocr?.text.toString().isEmpty()) {
                values.putNull("ocr")
            } else {
                values.put("ocr", ocr?.text.toString())
            }

            // アップデート
            database.update("Photo", values, "id=$photoID", null)

            // スナックバー表示
            view?.let {
                Snackbar.make(it, "保存しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") { activity?.finish() }
                    .setActionTextColor(Color.YELLOW)
                    .show()
            }

        } catch (exception: Exception) {
            Log.e("updateData", exception.toString())
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}