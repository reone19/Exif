package com.example.exif

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    private lateinit var item: Photo
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        val state = intent.getSerializableExtra("hoge")
        if (state is Photo) {
            item = state
            createOrUpdate()
        }
    }

    private fun createOrUpdate() {
        val photo = Photo(item.photoId, item.photoName, item.photoURL, item.deleteDate)
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(photo)
        realm.commitTransaction()
    }
}
