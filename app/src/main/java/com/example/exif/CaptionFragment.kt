package com.example.exif

import android.content.ContentValues
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentCaptionBinding


class CaptionFragment : Fragment() {

    // バインディング
    private var _binding: FragmentCaptionBinding? = null
    private val binding get() = _binding!!

    private var arrayListPhotoId: ArrayList<String> = arrayListOf()
    private var arrayListimageSentence1: ArrayList<String> = arrayListOf()
    private var arrayListimageSentence2: ArrayList<String> = arrayListOf()
    private var arrayListimageSentence3: ArrayList<String> = arrayListOf()
    private var sentence1:String? = null
    private var sentence2:String? = null
    private var sentence3:String? = null

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
                "select id,sentence1,sentence2,sentence3 from photo where id = " + photoID
            val cursor = databaseR.rawQuery(sql, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    arrayListPhotoId.add(cursor.getString(0))
                    arrayListimageSentence1.add(cursor.getString(1))
                    arrayListimageSentence2.add(cursor.getString(2))
                    arrayListimageSentence3.add(cursor.getString(3))
                    cursor.moveToNext()
                }
            }
            // 画像のキャプション
            try {
                // 画像のキャプション
                sentence1 = arrayListimageSentence1.get(0)
            } catch (e: NullPointerException){

            }
            try {
                // 画像のキャプション
                sentence2 = arrayListimageSentence2.get(0)
            } catch (e: NullPointerException){

            }
            try {
                // 画像のキャプション
                sentence3 = arrayListimageSentence3.get(0)
            } catch (e: NullPointerException){

            }
            // データセット
            binding.capString1.setText(sentence1)
            binding.capString2.setText(sentence2)
            binding.capString3.setText(sentence3)

        }catch (e: SQLiteConstraintException){

        }

        binding.btn.setOnClickListener { view ->
            UpdateCaption()
        }
        return binding.root
    }

    private fun UpdateCaption(){
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
        //とりあえずインスタンス化した。
        //val cat: String
        val caption1 = view?.findViewById<EditText>(R.id.capString1)
        val caption2 = view?.findViewById<EditText>(R.id.capString2)
        val caption3 = view?.findViewById<EditText>(R.id.capString3)
        //なんでネコ？
        //cat = caption1?.text.toString()
        //cat = caption2?.text.toString()
        //cat = caption3?.text.toString()
        try {
            val database = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("sentence1",caption1?.text.toString())
            values.put("sentence2",caption2?.text.toString())
            values.put("sentence3",caption3?.text.toString())
            database.update("Photo", values, "id="+photoID,null)

            // トースト表示
            val text = "保存しました。"
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(context, text, duration).show()

        }catch(exception: Exception) {
            Log.e("updateData", exception.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}