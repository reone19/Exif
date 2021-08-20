package com.example.exif

import android.content.ContentValues
import android.database.sqlite.SQLiteConstraintException
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

// viewPager2による更新で、フォト画面に行かないと表示されている値が更新されない問題解消のため
private var changeOcr: Array<String?> = arrayOfNulls(allImageId.size)

private var changeOcrFlag: Array<String?> = arrayOfNulls(allImageId.size)

class OcrFragment : Fragment() {

    // バインディング
    private var _binding: FragmentOcrBinding? = null
    private val binding get() = _binding!!

    // OCR
    private var _ocr: OCR? = null
    private var _ocr2: OCReng? = null

    // 格納配列＆変数
//    private var arrayListPhotoId: ArrayList<String> = arrayListOf()
//    private var arrayListImageOcr: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentOcrBinding.inflate(inflater, container, false)
//        //DB参照
//        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
//        try {
//            // データの取得処理
//            val databaseR = dbHelper.readableDatabase
//            val sql =
//                "SELECT id,ocr FROM photo WHERE id = $photoID"
//            val cursor = databaseR.rawQuery(sql, null)
//
//            if (cursor.count > 0) {
//                cursor.moveToFirst()
//                while (!cursor.isAfterLast) {
//                    arrayListPhotoId.add(cursor.getString(0))
//                    arrayListImageOcr.add(cursor.getString(1))
//                    cursor.moveToNext()
//                }
//            }
//        } catch (e: SQLiteConstraintException) {
//
//        }

        // 画像のキャプション

        //OCRが既にレコードにある場合
        // データセット（viewPager2によってセット）
        if (changeOcrFlag[imageResId!! - 1] == true.toString()) {
            binding.ocrString.setText(changeOcr[imageResId!! - 1])
        } else {
            binding.ocrString.setText(imageResOcr)
        }


        //OCRがレコードになかった場合
        //日本語OCRボタン
        binding.btn1.setOnClickListener {
            japanesesOcr()
        }
        //英語OCR用ボタン
        binding.btn2.setOnClickListener {
            englishOcr()
        }

        //アップデート用ボタン
        binding.btn3.setOnClickListener {
            updateOcr()
        }
        return binding.root
    }

    //日本語OCRメソッド
    private fun japanesesOcr() {

        _ocr = activity?.let { OCR(it.applicationContext) }

        // OCR
        val ocrString: String
        var bitmap: Bitmap? = null

        // URI取得
        val f: File = File(imageResPath)
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

        //データセット
        binding.ocrString.setText(ocrString)
    }

    //英語OCRメソッド
    private fun englishOcr() {
        _ocr2 = activity?.let { OCReng(it.applicationContext) }

        // OCR
        val ocrString: String
        var bitmap: Bitmap? = null

        // URI取得
        val f: File = File(imageResPath)
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
            activity?.applicationContext?.let { _ocr2!!.getString(it, bitmap) }
                .toString()
        } else {
            "bitmap is null"
        }

        //データセット
        binding.ocrString.setText(ocrString)
    }

    //アップデートメソッド
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

            // アップデート（viewPager2によるIDでアップデート）
            database.update("Photo", values, "id=$imageResId", null)

            //viewPager2のため、値を保持
            changeOcr[imageResId!! - 1] = ocr?.text.toString()

            changeOcrFlag[imageResId!! - 1] = true.toString()

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