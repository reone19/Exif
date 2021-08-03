package com.example.exif

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exif.model.Image
import com.example.exif.model.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //フィールドの記載
    private var imageRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var allPictures: ArrayList<Image>? = null

    // 自動再起動用の変数
    private var context: Context? = null
    private var waitperiod = 0
    private var flag: Boolean? = false

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // すべてにファイルを管理できるアクセス権限を付与（Exifの編集に必要）
        // 許可しない限りアプリが使用できない
        if (Environment.isExternalStorageManager()) {
            //todo when permission is granted
        } else {
            //request for the permission
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            flag = true
            intent.data = uri
            startActivity(intent)
        }

        val navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(bottom_navigation, navController)

//        リサイクルビューイメージのId定義
        imageRecycler = findViewById(R.id.image_recycler)
        progressBar = findViewById(R.id.recycler_progress)
        //リサイクルビューのグリットレイアウトで表示されている画像の制御、spanCountは4列で画像を並べてる意味
        imageRecycler?.layoutManager = GridLayoutManager(this, 4)
        //これで表示画像の大きさを均等になるよう修正を加えている。falseにしたら大変な事になる。
        imageRecycler?.setHasFixedSize(true)

//        // メディアへのアクセス権限を付与
//        if(ContextCompat.checkSelfPermission(
//                this@MainActivity,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            )!= PackageManager.PERMISSION_GRANTED
//        ){
//            ActivityCompat.requestPermissions(
//                this@MainActivity,
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),101
//            )
//        }

        allPictures = ArrayList()

        if (allPictures!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            //画像取得の際のプログレスバーの不可視設定かつimageRecyclerに
            // allpicturesの画像配列をセット。
            allPictures = getAllImages()
            //Adapterをリサイクラーにセットする
            imageRecycler?.adapter = PhotoAdapter(this, allPictures!!)
            progressBar?.visibility = View.GONE
        }

    }

    //外部ストレージからすべての画像を取得するメソッドの設定
    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor =
            this@MainActivity.contentResolver.query(allImageUri, projection, null, null, null)

        try {
            cursor!!.moveToFirst()
            do {
                val image = Image()
                image.imagePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }


    private fun restart(cnt: Context, period: Int) {
        // intent 設定で自分自身のクラスを設定
        val mainActivity = Intent(cnt, MainActivity::class.java)

        // PendingIntent , ID=0
        val pendingIntent = PendingIntent.getActivity(
            cnt,
            0, mainActivity, PendingIntent.FLAG_CANCEL_CURRENT
        )

        // AlarmManager のインスタンス生成
        val alarmManager = cnt.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager

        // １回のアラームを現在の時間からperiod（x秒）後に実行させる
        if (alarmManager != null) {
            val trigger = System.currentTimeMillis() + period
            alarmManager.setExact(AlarmManager.RTC, trigger, pendingIntent)
        }

        // アプリ終了
        finish()
    }

    override fun onResume() {
        super.onResume()

        // 権限を付与後、再起動しないとメディアから読み込まれないため、自動で再起動にするように設定したが
        // 上手くいかないため、権限を変更後は自動で停止するようにした。
        // （スリープ時間を限りなく大きくすることで）
        if (flag == true) {
            context = applicationContext
            waitperiod = 100000000
            context?.let { restart(it, waitperiod) }
            flag = false
        }
    }
}