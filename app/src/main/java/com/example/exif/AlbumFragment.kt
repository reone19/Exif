package com.example.exif

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.*
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment


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

        //ポップアップをインスタンス化、非表示
        val popup = view.findViewById<LinearLayout>(R.id.popup)
        popup.visibility = View.INVISIBLE

        //「追加」を押したときにポップアップを追加
        val addButton = view.findViewById<Button>(R.id.add_album)
        addButton.setOnClickListener{
            //ポップアップを表示
            popup.visibility = View.VISIBLE
        }

        //「保存」を押したときにアルバムを追加
        val saveBtn = view.findViewById<Button>(R.id.btn_save)
        saveBtn.setOnClickListener{
            //IDを変数で指定
            val image = "image_id$a"
            val text = "text$a"
            val imageId = resources.getIdentifier(image, "id", "com.example.exif")
            val textId = resources.getIdentifier(text, "id", "com.example.exif")
            //空の写真をインスタンス化
            val imagesourse = resources.getIdentifier("noimage", "drawable", "com.example.exif")
            //新しいID生成
            val imagenewId = IntArray(999)
            imagenewId[b] = ViewCompat.generateViewId()
            val textnewId = IntArray(999)
            textnewId[b] = ViewCompat.generateViewId()
            if (a%3 == 0){
                //「MainLinearLayout」というidに「album_sub.xml」を追加
                val Main_sub = view.findViewById<LinearLayout>(R.id.MainLinearLayout)
                layoutInflater.inflate(R.layout.album_sub,Main_sub)

                //新しく生成したIDをセットする
                val imageview = view.findViewById<ImageButton>(imageId)
                val textview = view.findViewById<TextView>(textId)
                imageview.setId(imagenewId[b])
                textview.setId(textnewId[b])
                val tv = view.findViewById<TextView>(textnewId[b])

                val edittext = view.findViewById<EditText>(R.id.editTexttitle)
                val title = edittext.text
                tv.setText(title.toString())
            }
            else{
                //新しく生成したIDをセットする
                val imageview = view.findViewById<ImageButton>(imageId)
                val textview = view.findViewById<TextView>(textId)
                imageview.setId(imagenewId[b])
                textview.setId(textnewId[b])
                val tv = view.findViewById<TextView>(textnewId[b])

                val edittext = view.findViewById<EditText>(R.id.editTexttitle)
                val title = edittext.text
                tv.setText(title.toString())
                //空の写真をnoimageに
                imageview.setImageResource(imagesourse)
            }
            a = a + 1
            b = b + 1

            //1列追加したらカウンタを0に
            if (a == 3){
                a = 0;
            }
            popup.visibility = View.INVISIBLE
        }

        //「キャンセル」を押したときにポップアップを非表示に
        val cancelBtn = view.findViewById<Button>(R.id.btn_cancel)
        cancelBtn.setOnClickListener{
            //ポップアップを非表示
            popup.visibility = View.INVISIBLE
        }
    }
}