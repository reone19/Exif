package com.example.exif

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.exif.databinding.ActivityPhotoDetailBinding

// 画像のパス
var imagePath: String? = null
var imageName: String? = null
var imageSize: Long? = null
var photoID: String? = null

class PhotoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDetailBinding

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
        imageSize = intent.getLongExtra("size", 0)
        val SizeStr: String = Formatter.formatFileSize(this, imageSize!!)
        val resultImage = findViewById<ImageView>(R.id.imageView)
        // スマホトップ左に画像の名前を表示
        supportActionBar?.title = imageName
        // アプリバーの表示
        // 戻るボタン
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val resultSize = findViewById<TextView>(R.id.textView)
        resultSize.setText("サイズ："+SizeStr)



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
    }
    // アプリバーの戻るボタンを押したときにfinish
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}