package com.example.exif

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exif.databinding.ActivityMainBinding
import com.example.exif.model.Meta
import com.example.exif.model.Photo
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var realm: Realm
    // private val args: MainActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // インスタンス取得 (初期化なのでDB処理を行うときはonCreateメソッドに必ず書く)
        realm = Realm.getDefaultInstance()


        //削除ボタンを押したときにdeleteExecuteを実行して該当レコードを削除
        binding.deleteBtn.setOnClickListener { deleteExecute(it) }

        // RecyclerViewにアダプターとレイアウトマネージャーを設定する
        binding.list.layoutManager = LinearLayoutManager(context)
        val metas = realm.where<Meta>().findAll()
        val adapter = MetaAdapter(metas)
        binding.list.adapter = adapter
    }


    // Realmのインスタンスを破棄してリソース開放
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    // Create
    private fun insertExecute(view: View) {
        realm.executeTransaction { db: Realm ->
            // idのインクリメント処理はこれを使うか、共有プリファレンスに常に最大のid番号を登録しておき、
            // その値プラス1をする
            val maxId = db.where<Photo>().max("photoID")
            val nextId = (maxId?.toLong() ?: 0L) + 1L
            val photo = db.createObject<Photo>(nextId)

            // 各カラムにコンテンツプロバイダから取得した情報をセット(コンテンツプロバイダ周りはまだ)
            photo?.name = binding.NameEdit.text.toString()
            photo?.url = binding.urlEdit.text.toString()
        }


        // 簡易Insert文
        // private fun insertExecute(view: View)
        // realm.executeTransaction {
        // val tmp1 = Photo(1,”名前”,"URL", "NULL")
        // realm.insert(tmp1)
        // }
    }


    // Read
    private fun readExecute(view: View) {
        // ログに出力する簡易版
        // var read = realm.where(Photo::class.java)
        // .equalTo("photo_name", "*.jpg")
        // .findAll()
        // ログに出力？
        // Log.d(TAG, read.toString())


        // id番号に一致しているカラムを取得
        val photo = realm.where<Meta>()
            .equalTo("photoId", "id番号")
            .findFirst()

        // DBに保存されている各レコードをアプリケーションのXMLにセット
        binding.PhotoNameEdit.setText(photo?.name)
        binding.photoUrlEdit.setText(photo?.url)
    }


    // Update
    private fun updateExecute(view: View) {
//        val photo = realm.where<Photo>()
//            .equalTo("photoId", "id番号")
//            .findFirst()
//        binding.titleEdit.setText(photo?.title)
//        binding.detailEdit.setText(photo?.detail)
//
//        // 保存ボタン
        // binding.save.setOnClickListener{ saveExecute(it) }


        realm.executeTransaction { db: Realm ->
            val photo = realm.where<Photo>()
                .equalTo("photoId", "id番号")
                .findFirst()

            // XMLに入力されたテキストをDBに保存して更新
            photo?.name = binding.photoNameEdit.text.toString()
            photo?.url = binding.photoURLEdit.text.toString()


//       var update = realm.where(Photo::class.java)
//            .equalTo("photo_name", "*.jpg")
//            .findAll()
//        realm.executeTransaction {
//            update.updateAllFromRealm()
//
//        }

        }
    }


    // Delete
    private fun deleteExecute(view: View) {
//        var delete = realm.where(Photo::class.java)
//            .equalTo("photo_name", "*.jpg")
//            .findAll()
        //削除
//        realm.executeTransaction {
//            delete.deleteAllFromRealm()


        realm.executeTransaction { db: Realm ->
            db.where<Photo>().equalTo("id", "id番号")
                ?.findFirst()
                ?.deleteFromRealm()
        }
    }
}
