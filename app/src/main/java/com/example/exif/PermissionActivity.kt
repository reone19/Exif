package com.example.exif

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionActivity : AppCompatActivity() {



    private val RECORD_REQUEST_CODE = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()
    }


    //パーミッションのチェックを設定するためのメソッド
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)

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