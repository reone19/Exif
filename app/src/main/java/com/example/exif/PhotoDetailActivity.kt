package com.example.exif

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.exif.databinding.ActivityPhotoDetailBinding

// 画像のパス
var imagePath: String? = null
var imageName: String? = null
var photoID: String? = null

// viewPager2で使う配列型変数
// var allImagePath: Array<String?> = emptyArray()
var allImageName: Array<String?> = emptyArray()
var allImageSentence1: Array<String?> = emptyArray()
var allImageSentence2: Array<String?> = emptyArray()
var allImageSentence3: Array<String?> = emptyArray()


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
        val resultImage = findViewById<ImageView>(R.id.imageView)

        // アプリバーの表示
        // 戻るボタン
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // グリットレイアウトの画像のため、画像をGlideで画像のパスを取得、xmlの画像IDと紐づけて、画像を出力している。
        // 結果表示ImageViewの準備
//        Glide.with(this).load(imagePath).into(resultImage)


        // viewPager2
        binding.pager.adapter = MyAdapter(this)

        // 初期位置を決める
        // これで左スライドもできる
        binding.pager.setCurrentItem(allImagePath.indexOf(imagePath), false)

        // viewPagerのデザイン
        binding.pager.setPageTransformer { page, position ->
            page.also {
                if (kotlin.math.abs(position) >= 1f) {
                    it.alpha = 0f
                    return@setPageTransformer
                }
                if (position > 0) {
                    it.alpha = 1 - position
                    val scale = 1f - position / 4f
                    it.scaleX = scale
                    it.scaleY = scale
                    it.translationX = -it.width * position
                    it.translationZ = -1f
                } else {
                    it.alpha = 1f
                    it.scaleX = 1f
                    it.scaleY = 1f
                    it.translationX = 0f
                    it.translationZ = 0f
                }
            }
        }

        //データベース接続
        val dbHelper = SampleDBHelper(this, "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase

        val sqlImagePath = "SELECT path FROM Photo"
        val sqlImageName = "SELECT name FROM Photo"

        val sqlSentence1 = "SELECT sentence1 FROM Photo"
        val sqlSentence2 = "SELECT sentence2 FROM Photo"
        val sqlSentence3 = "SELECT sentence3 FROM Photo"

        val cursorImagePath = databaseR.rawQuery(sqlImagePath, null)
        val cursorImageName = databaseR.rawQuery(sqlImageName, null)

        val cursorSentence1 = databaseR.rawQuery(sqlSentence1, null)
        val cursorSentence2 = databaseR.rawQuery(sqlSentence2, null)
        val cursorSentence3 = databaseR.rawQuery(sqlSentence3, null)

//        try {
//            cursorImagePath.moveToFirst()
//            allImagePath = arrayOfNulls(cursorImagePath.count)
//            for (i in allImagePath.indices) {
//                allImagePath[i] = cursorImagePath.getString(0)
//                cursorImagePath.moveToNext()
//            }
//            cursorImagePath.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

        try {
            cursorImageName.moveToFirst()
            allImageName = arrayOfNulls(cursorImageName.count)
            for (i in allImageName.indices) {
                allImageName[i] = cursorImageName.getString(0)
                cursorImageName.moveToNext()
            }
            cursorImageName.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorSentence1.moveToFirst()
            allImageSentence1 = arrayOfNulls(cursorSentence1.count)
            for (i in allImageSentence1.indices) {
                allImageSentence1[i] = cursorSentence1.getString(0)
                cursorSentence1.moveToNext()
            }
            cursorSentence1.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorSentence2.moveToFirst()
            allImageSentence2 = arrayOfNulls(cursorSentence2.count)
            for (i in allImageSentence2.indices) {
                allImageSentence2[i] = cursorSentence1.getString(0)
                cursorSentence2.moveToNext()
            }
            cursorSentence2.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorSentence3.moveToFirst()
            allImageSentence3 = arrayOfNulls(cursorSentence3.count)
            for (i in allImageSentence1.indices) {
                allImageSentence3[i] = cursorSentence3.getString(0)
                cursorSentence3.moveToNext()
            }
            cursorSentence3.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    // viewPager2
    class MyAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        // 要素の数を取得
        @RequiresApi(Build.VERSION_CODES.N)
        override fun getItemCount(): Int = allImagePath.size


        // viewPager2で渡すデータ
        @RequiresApi(Build.VERSION_CODES.N)
        override fun createFragment(position: Int): Fragment =
            PhotoDetailFragment.newInstance(
                allImagePath[position],
                allImageName[position],
                allImageSentence1[position],
                allImageSentence2[position],
                allImageSentence3[position],
            )
    }


    // アプリバーの戻るボタンを押したときにfinish
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}