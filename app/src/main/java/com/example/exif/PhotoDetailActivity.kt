package com.example.exif

import android.content.ContentValues
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.exif.databinding.ActivityPhotoDetailBinding
import com.example.exif.model.Image
import com.example.exif.model.PhotoAdapter
import java.io.File
import java.io.IOException
import java.io.InputStream

// 画像のパス
var imagePath: String? = null
var imageName: String? = null
var photoID: String? = null



class PhotoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDetailBinding

    var a: Int = 1


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 画像のパスを受け取るためのデータ
        imagePath = intent.getStringExtra("path")
        // 画像の名前を受け取るためのデータ
        imageName = intent.getStringExtra("name")
        photoID = intent.getStringExtra("id")
        val resultImage = findViewById<ImageView>(R.id.imageView)

        // アプリバーの表示
        // 戻るボタン
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // タイトル
        supportActionBar?.title = imageName


        // グリットレイアウトの画像のため、画像をGlideで画像のパスを取得、xmlの画像IDと紐づけて、画像を出力している。
        // 結果表示ImageViewの準備
        Glide.with(this).load(imagePath).into(resultImage)

        // キャプションフラグメントをデフォルト表示にする
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, CaptionFragment())
            addToBackStack(null)
            commit()
        }

        // キャプションボタンをクリックしたときのフラグメント動作
        binding.captionButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, CaptionFragment())
                addToBackStack(null)
                commit()
            }
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#dddddd")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#ffffff")))
        }

        // Exifボタンをクリックしたときのフラグメント動作
        binding.exifButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, ExifFragment())
                addToBackStack(null)
                commit()
            }
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#dddddd")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#ffffff")))
        }

        // OCRボタンをクリックしたときのフラグメント動作
        binding.ocrButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, OcrFragment())
                addToBackStack(null)
                commit()
            }
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#dddddd")))
        }

        binding.pager.adapter = MyAdapter(this)
    }


    // アプリバーの戻るボタンを押したときにfinish
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


    class MyAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        private var allPictures = getAllImages()

        private val resources =  PhotoAdapter(this, allPictures!!)

        override fun getItemCount(): Int = resources.size

        PhotoAdapter()

        override fun createFragment(position: Int): Fragment =
            PhotoDetailFragment.newInstance(resources[position])

    }


    // 外部ストレージからすべての画像を取得するメソッドの設定
    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor =
            this@PhotoDetailActivity.contentResolver.query(
                allImageUri,
                projection,
                null,
                null,
                null
            )

        try {
            cursor!!.moveToFirst()
            do {
                val image = Image()
                image.imageId = b.toString()
                image.imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                for (i in 0 until a) {
                    if (image.imageName == arrayListPhotoName[i]) {
                        images.add(image)
                    }
                }
                b += 1
            } while (cursor.moveToNext())
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return images
    }

}