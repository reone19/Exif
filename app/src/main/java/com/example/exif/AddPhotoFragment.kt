package com.example.exif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteConstraintException
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exif.model.Image
import com.example.exif.model.PhotoAdapter
import com.example.exif.model.PhotoAdapterAlbum


class AddPhotoFragment : AppCompatActivity() {
    var a:Int = 1
    var albumID = ""

    //フィールドの記載
    private var imageRecycler: RecyclerView? = null
    private var  progressBar: ProgressBar?=null
    private var allPictures:ArrayList<Image>?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo_fragment)

        albumID= intent.getStringExtra("album_id").toString()
        Log.d("Albumid", albumID)

        val okButton = findViewById<Button>(R.id.ok)
        okButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        リサイクルビューイメージのId定義
        imageRecycler = findViewById(R.id.image_recycler)
        progressBar = findViewById(R.id.recycler_progress)
        //リサイクルビューのグリットレイアウトで表示されている画像の制御、spanCountは4列で画像を並べてる意味
        imageRecycler?.layoutManager=GridLayoutManager(this,4)
        //これで表示画像の大きさを均等になるよう修正を加えている。falseにしたら大変な事になる。
        imageRecycler?.setHasFixedSize(true)

        if(ContextCompat.checkSelfPermission(
                this@AddPhotoFragment,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )!= PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this@AddPhotoFragment,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),101
            )
        }

        allPictures= ArrayList()

        if(allPictures!!.isEmpty())
        {
            progressBar?.visibility= View.VISIBLE
            //画像取得の際のプログレスバーの不可視設定かつimageRecyclerに
            // allpicturesの画像配列をセット。
            allPictures=getAllImages()
            //Adapterをリサイクラーにセットする
            imageRecycler?.adapter= PhotoAdapterAlbum(this,allPictures!!)
            progressBar?.visibility=View.GONE
        }

    }
    //外部ストレージからすべての画像を取得するメソッドの設定
    private fun getAllImages(): ArrayList<Image>?{
        val images = ArrayList<Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor =
            this@AddPhotoFragment.contentResolver.query(allImageUri, projection, null, null, null)

        try {
            cursor!!.moveToFirst()
            do {
                val image = Image()
                image.imageid = a.toString()
                image.albumid = albumID
                image.imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
                a = a + 1
            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }
}