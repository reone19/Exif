package com.example.exif

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentCaptionBinding
import com.example.exif.model.Image
import java.io.File
import java.io.IOException
import java.io.InputStream


class CaptionFragment : Fragment() {

    // バインディング
    private var _binding: FragmentCaptionBinding? = null
    private val binding get() = _binding!!

    private var arrayListPhotoId: ArrayList<String> = arrayListOf()
    private var arrayListimageSentence: ArrayList<String> = arrayListOf()
    private var sentence:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaptionBinding.inflate(inflater, container, false)

        //Nakao君のマネしちゃおっとヂュフフ(*´ω｀*)

        //データベース接続
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
        try{
            // データの取得処理
            val databaseR = dbHelper.readableDatabase
            val sql =
                "select id,sentence from photo where id = " + photoID
            val cursor = databaseR.rawQuery(sql, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    arrayListPhotoId.add(cursor.getString(0))
                    arrayListimageSentence.add(cursor.getString(1))
                    cursor.moveToNext()
                }
            }
            // 画像のキャプション
            try {
                // 画像のキャプション
                sentence = arrayListimageSentence.get(0)
            } catch (e: NullPointerException){

            }
            // データセット
            binding.capString.setText(sentence)

        }catch (e: SQLiteConstraintException){

        }

        binding.btn.setOnClickListener { view ->
            UpdateCaption()
        }
        return binding.root
    }

    fun UpdateCaption(){
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
        //とりあえずインスタンス化した。
        val cat: String
        val caption = view?.findViewById<EditText>(R.id.capString)
        //なんでネコ？
        cat = caption?.text.toString()
        try {
            val database = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("sentence",cat)
            database.update("Photo", values, "id="+photoID,null)

        }catch(exception: Exception) {
            Log.e("updateData", exception.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}