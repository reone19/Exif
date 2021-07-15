package com.example.exif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class YoshidanoYatu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoshidano_yatu)

        val imagePath= intent.getStringExtra("path")
        val imageName= intent.getStringExtra("name")

        supportActionBar?.setTitle(imageName)
        Glide.with(this)
            .load(imagePath)
            .into(findViewById(R.id.imageView))

    }
}