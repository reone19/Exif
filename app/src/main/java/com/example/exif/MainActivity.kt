package com.example.exif

import android.os.Bundle


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import com.example.exif.databinding.ActivityMainBinding
import android.view.View
//realm
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import com.example.exif.model.Photo
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    //realmの定義
    //private lateinit var realm: Realm
    //private val args: MainActivityArgs by navArgs()


    private lateinit var binding: ActivityMainBinding
    //ギャラリーのクラス変数の定義
    private val REQUEST_GALLERY_TAKE = 2
    private val RECORD_REQUEST_CODE = 1000
    //イメージビューのフィールド定義
    private lateinit var storage_iv: ImageView
    private lateinit var storage_btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //配列化して、表示できるようにすればいい気がする
        storage_iv = findViewById<ImageView>(R.id.storage_iv)
        storage_btn = findViewById<Button>(R.id.storage_btn)
        setupPermissions()

        //EditTextのクリックイベントを設定
        storage_btn.setOnClickListener {
            openGalleryForImage()
        }
    }

    //ギャラリーを開くためのメソッド
    private fun openGalleryForImage() {
        //ギャラリーに画面を遷移するためのIntent
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }


    // onActivityResultにイメージ設定
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
                    //選択された写真にImageViewを変更
                    storage_iv.setImageURI(data?.data) // handle chosen image
                }
            }
        }
    }

    //パーミッションのチェックを設定するためのメソッド
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        //権限の許可がされてない場合、makeRequest()メソッドを使ってリクエストをスル。眠い。。。
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    //パーミッションをリクエストするためのメソッド
    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE)
    }

    //パーミッションの許可の結果による実行されるメソッド
    //Toastを使って、許可された状態か？否かを確認できるようにメソッドを作成。基本常に許可状態なので、パーミッションのリクエストとかのアクションキーないのであんま意味ないかも
    //まぁ、確認用に・・
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            RECORD_REQUEST_CODE ->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "デバイス内の写真やメディアへのアクセスが許可されませんでした。", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "デバイス内の写真やメディアへのアクセスが許可されました。", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}
