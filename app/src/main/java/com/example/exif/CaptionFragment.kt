package com.example.exif

import android.content.ContentValues
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentCaptionBinding
import com.google.android.material.snackbar.Snackbar


class CaptionFragment : Fragment() {

    // バインディング
    private var _binding: FragmentCaptionBinding? = null
    private val binding get() = _binding!!

    private var arrayListPhotoId: ArrayList<String> = arrayListOf()
    private var arrayListImageSentence1: ArrayList<String> = arrayListOf()
    private var arrayListImageSentence2: ArrayList<String> = arrayListOf()
    private var arrayListImageSentence3: ArrayList<String> = arrayListOf()
    private var sentence1: String? = null
    private var sentence2: String? = null
    private var sentence3: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaptionBinding.inflate(inflater, container, false)

        // データベース接続
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
        try {
            // データの取得処理
            val databaseR = dbHelper.readableDatabase
            val sql =
                "SELECT id,sentence1,sentence2,sentence3 FROM photo WHERE id = $photoID"
            val cursor = databaseR.rawQuery(sql, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    arrayListPhotoId.add(cursor.getString(0))
                    arrayListImageSentence1.add(cursor.getString(1))
                    arrayListImageSentence2.add(cursor.getString(2))
                    arrayListImageSentence3.add(cursor.getString(3))
                    cursor.moveToNext()
                }
            }
            // 画像のキャプション
            try {
                // 画像のキャプション
                sentence1 = arrayListImageSentence1[0]
            } catch (e: NullPointerException) {

            }
            try {
                // 画像のキャプション
                sentence2 = arrayListImageSentence2[0]
            } catch (e: NullPointerException) {

            }
            try {
                // 画像のキャプション
                sentence3 = arrayListImageSentence3[0]
            } catch (e: NullPointerException) {

            }
            // データセット
            binding.capString1.setText(sentence1)
            binding.capString2.setText(sentence2)
            binding.capString3.setText(sentence3)

            // ログ　後で削除
            Log.e("capString1Tab", binding.capString1.text.toString())

        } catch (e: SQLiteConstraintException) {

        }

        binding.btn.setOnClickListener {
            updateCaption()
        }
        return binding.root
    }

    private fun updateCaption() {
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

        val caption1 = view?.findViewById<EditText>(R.id.capString1)
        val caption2 = view?.findViewById<EditText>(R.id.capString2)
        val caption3 = view?.findViewById<EditText>(R.id.capString3)

        try {
            val database = dbHelper.writableDatabase
            val values = ContentValues()

            // 空文字のときはnullを挿入
            if (caption1?.text.toString().isEmpty()) {
                values.putNull("sentence1")
            } else {
                values.put("sentence1", caption1?.text.toString())
            }

            if (caption2?.text.toString().isEmpty()) {
                values.putNull("sentence2")
            } else {
                values.put("sentence2", caption2?.text.toString())
            }

            if (caption3?.text.toString().isEmpty()) {
                values.putNull("sentence3")
            } else {
                values.put("sentence3", caption3?.text.toString())
            }

            // 一括アップデート
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