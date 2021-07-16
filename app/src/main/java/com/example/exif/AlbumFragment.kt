package com.example.exif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
class AlbumFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_album, container, false)
    }
    //アルバムレイアウトカウンタ(横に3つImageBottonを作る)
    var a:Int = 0
    //アルバムID用カウンタ
    var b:Int = 0
    companion object {
        private const val TAG = "AlbumFragment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backButton = view.findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener{
            Log.d(TAG, "BackButton pressed!")
            fragmentManager?.popBackStack()
        }
        val popup = view.findViewById<LinearLayout>(R.id.popup)
        popup.visibility = View.INVISIBLE
        //「追加」を押したときにレイアウトを追加
        val addButton = view.findViewById<Button>(R.id.add_album)
        addButton.setOnClickListener{
            //IDを変数で指定
            val image = "image_id$a"
            val text = "text$a"
            val imageId = resources.getIdentifier(image, "id", "com.example.exif")
            val textId = resources.getIdentifier(text, "id", "com.example.exif")
            //空の写真をインスタンス化
            val imagesourse = resources.getIdentifier("noimage", "drawable", "com.example.exif")
            //新しいID生成
            val newId = IntArray(999)
            newId[b] = ViewCompat.generateViewId()
            if (a%3 == 0){
                //「MainLinearLayout」というidに「album_sub.xml」を追加
                val Main_sub = view.findViewById<LinearLayout>(R.id.MainLinearLayout)
                layoutInflater.inflate(R.layout.album_sub,Main_sub)
                //新しく生成したIDをセットする
                val imageview = view.findViewById<ImageButton>(imageId)
                val tv = view.findViewById<TextView>(textId)
                imageview.setId(newId[b])
                tv.setText(newId[b].toString())
            }
            else{
                //新しく生成したIDをセットする
                val imageview = view.findViewById<ImageButton>(imageId)
                val tv = view.findViewById<TextView>(textId)
                imageview.setId(newId[b])
                tv.setText(newId[b].toString())
                //空の写真をnoimageに
                imageview.setImageResource(imagesourse)
            }
            a = a + 1
            b = b + 1
            //1列追加したらカウンタを0に
            if (a == 3){
                a = 0;
            }
        }
    }
}