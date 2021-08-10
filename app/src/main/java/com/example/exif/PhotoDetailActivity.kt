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
var allImageId: Array<Int?> = emptyArray()
// var allImagePath: Array<String?> = emptyArray()
var allImageName: Array<String?> = emptyArray()
var allImageSentence1: Array<String?> = emptyArray()
var allImageSentence2: Array<String?> = emptyArray()
var allImageSentence3: Array<String?> = emptyArray()

var allPhotoId: Array<Int?> = emptyArray()
var allExifImageLength: Array<String?> = emptyArray()
var allExifImageWidth: Array<String?> = emptyArray()
var allExifYResolution: Array<String?> = emptyArray()
var allExifXResolution: Array<String?> = emptyArray()
var allExifBitsPerSample: Array<String?> = emptyArray()
var allExifCompression: Array<String?> = emptyArray()
var allExifImageOrientation: Array<String?> = emptyArray()
var allExifImageDescription: Array<String?> = emptyArray()
var allExifArtist: Array<String?> = emptyArray()
var allExifMaker: Array<String?> = emptyArray()
var allExifModel: Array<String?> = emptyArray()
var allExifAperture: Array<String?> = emptyArray()
var allExifExposureTime: Array<String?> = emptyArray()
var allExifIsoSpeed: Array<String?> = emptyArray()
var allExifExposureBias: Array<String?> = emptyArray()
var allExifFNumber: Array<String?> = emptyArray()
var allExifShutterSpeed: Array<String?> = emptyArray()
var allExifFocalLength: Array<String?> = emptyArray()
var allExifMeteringMode: Array<String?> = emptyArray()
var allExifFlash: Array<String?> = emptyArray()
var allExifStripOffsets: Array<String?> = emptyArray()
var allExifGpsVersionID: Array<String?> = emptyArray()
var allExifGpsLatitude: Array<String?> = emptyArray()
var allExifGpsLongitude: Array<String?> = emptyArray()
var allExifGpsAltitude: Array<String?> = emptyArray()
var allExifDateTimeOriginal: Array<String?> = emptyArray()
var allExifChangeDateAndTime: Array<String?> = emptyArray()


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
        // Glide.with(this).load(imagePath).into(resultImage)


        // viewPager2
        binding.pager.adapter = MyAdapter(this)

        // 初期位置を決める
        // これで左スライドもできる
        binding.pager.setCurrentItem(allImagePath.indexOf(imagePath), false)

        var itemNumber: Int? =  binding.pager.currentItem

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

        val sqlImageId = "SELECT id FROM Photo"
        val sqlImagePath = "SELECT path FROM Photo"
        val sqlImageName = "SELECT name FROM Photo"
        val sqlSentence1 = "SELECT sentence1 FROM Photo"
        val sqlSentence2 = "SELECT sentence2 FROM Photo"
        val sqlSentence3 = "SELECT sentence3 FROM Photo"

        val sqlPhotoId = "SELECT photo_id FROM Meta"
        val sqlImageLength = "SELECT image_length FROM Meta"
        val sqlImageWidth = "SELECT image_width FROM Meta"
        val sqlYResolution = "SELECT y_resolution FROM Meta"
        val sqlXResolution = "SELECT x_resolution FROM Meta"
        val sqlBitsPerSample = "SELECT bits_per_sample FROM Meta"
        val sqlCompression = "SELECT compression FROM Meta"
        val sqlImageOrientation = "SELECT image_orientation FROM Meta"
        val sqlImageDescription = "SELECT image_description FROM Meta"
        val sqlArtist = "SELECT artist FROM Meta"
        val sqlMaker = "SELECT maker FROM Meta"
        val sqlModel = "SELECT model FROM Meta"
        val sqlAperture = "SELECT aperture FROM Meta"
        val sqlExposureTime = "SELECT exposure_time FROM Meta"
        val sqlIsoSpeed = "SELECT iso_speed FROM Meta"
        val sqlExposureBias = "SELECT exposure_bias FROM Meta"
        val sqlFNumber = "SELECT f_number FROM Meta"
        val sqlShutterSpeed = "SELECT shutter_speed FROM Meta"
        val sqlFocalLength = "SELECT focal_length FROM Meta"
        val sqlMeteringMode = "SELECT metering_mode FROM Meta"
        val sqlFlash = "SELECT flash FROM Meta"
        val sqlStripOffsets = "SELECT strip_offsets FROM Meta"
        val sqlGpsVersionID = "SELECT gps_version_id FROM Meta"
        val sqlGpsLatitude = "SELECT gps_latitude FROM Meta"
        val sqlGpsLongitude = "SELECT gps_longitude FROM Meta"
        val sqlGpsAltitude = "SELECT gps_altitude FROM Meta"
        val sqlDateTimeOriginal = "SELECT date_time_original FROM Meta"
        val sqlChangeDateAndTime = "SELECT change_date_and_time FROM Meta"


        val cursorImageId = databaseR.rawQuery(sqlImageId, null)
        val cursorImagePath = databaseR.rawQuery(sqlImagePath, null)
        val cursorImageName = databaseR.rawQuery(sqlImageName, null)
        val cursorSentence1 = databaseR.rawQuery(sqlSentence1, null)
        val cursorSentence2 = databaseR.rawQuery(sqlSentence2, null)
        val cursorSentence3 = databaseR.rawQuery(sqlSentence3, null)

        val cursorPhotoId =databaseR.rawQuery(sqlPhotoId, null)
        val cursorImageLength =databaseR.rawQuery(sqlImageLength, null)
        val cursorImageWidth =databaseR.rawQuery(sqlImageWidth, null)
        val cursorYResolution = databaseR.rawQuery(sqlYResolution, null)
        val cursorXResolution = databaseR.rawQuery(sqlXResolution, null)
        val cursorBitsPerSample =databaseR.rawQuery(sqlBitsPerSample, null)
        val cursorCompression = databaseR.rawQuery(sqlCompression, null)
        val cursorImageOrientation = databaseR.rawQuery(sqlImageOrientation, null)
        val cursorImageDescription = databaseR.rawQuery(sqlImageDescription, null)
        val cursorArtist =databaseR.rawQuery(sqlArtist, null)
        val cursorMaker = databaseR.rawQuery(sqlMaker, null)
        val cursorModel = databaseR.rawQuery(sqlModel, null)
        val cursorAperture = databaseR.rawQuery(sqlAperture, null)
        val cursorExposureTime = databaseR.rawQuery(sqlExposureTime, null)
        val cursorIsoSpeed = databaseR.rawQuery(sqlIsoSpeed, null)
        val cursorExposureBias = databaseR.rawQuery(sqlExposureBias, null)
        val cursorFNumber = databaseR.rawQuery(sqlFNumber, null)
        val cursorShutterSpeed = databaseR.rawQuery(sqlShutterSpeed, null)
        val cursorFocalLength = databaseR.rawQuery(sqlFocalLength, null)
        val cursorMeteringMode = databaseR.rawQuery(sqlMeteringMode, null)
        val cursorFlash = databaseR.rawQuery(sqlFlash, null)
        val cursorStripOffsets = databaseR.rawQuery(sqlStripOffsets, null)
        val cursorGpsVersionID = databaseR.rawQuery(sqlGpsVersionID, null)
        val cursorGpsLatitude = databaseR.rawQuery(sqlGpsLatitude, null)
        val cursorGpsLongitude = databaseR.rawQuery(sqlGpsLongitude, null)
        val cursorGpsAltitude = databaseR.rawQuery(sqlGpsAltitude, null)
        val cursorDateTimeOriginal =databaseR.rawQuery(sqlDateTimeOriginal, null)
        val cursorChangeDateAndTime =databaseR.rawQuery(sqlChangeDateAndTime, null)


        // 共通
        try {
            cursorImageId.moveToFirst()
            allImageId = arrayOfNulls(cursorImageId.count)
            for (i in allImageId.indices) {
                allImageId[i] = cursorImageId.getInt(0)
                cursorImageId.moveToNext()
            }
            cursorImageId.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

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

        // キャプション
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
                allImageSentence2[i] = cursorSentence2.getString(0)
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

        // Exif
        try {
            cursorPhotoId.moveToFirst()
            allPhotoId = arrayOfNulls(cursorPhotoId.count)
            for (i in allPhotoId.indices) {
                allPhotoId[i] = cursorPhotoId.getInt(0)
                cursorPhotoId.moveToNext()
            }
            cursorPhotoId.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorImageLength.moveToFirst()
            allExifImageLength = arrayOfNulls(cursorImageLength.count)
            for (i in allExifImageLength.indices) {
                allExifImageLength[i] = cursorImageLength.getString(0)
                cursorImageLength.moveToNext()
            }
            cursorImageLength.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorImageWidth.moveToFirst()
            allExifImageWidth = arrayOfNulls(cursorImageWidth.count)
            for (i in allExifImageWidth.indices) {
                allExifImageWidth[i] = cursorImageWidth.getString(0)
                cursorImageWidth.moveToNext()
            }
            cursorImageWidth.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorYResolution.moveToFirst()
            allExifYResolution = arrayOfNulls(cursorYResolution.count)
            for (i in allExifYResolution.indices) {
                allExifYResolution[i] = cursorYResolution.getString(0)
                cursorYResolution.moveToNext()
            }
            cursorYResolution.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorXResolution.moveToFirst()
            allExifXResolution = arrayOfNulls(cursorXResolution.count)
            for (i in allExifXResolution.indices) {
                allExifXResolution[i] = cursorXResolution.getString(0)
                cursorXResolution.moveToNext()
            }
            cursorXResolution.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorBitsPerSample.moveToFirst()
            allExifBitsPerSample = arrayOfNulls(cursorBitsPerSample.count)
            for (i in allExifBitsPerSample.indices) {
                allExifBitsPerSample[i] = cursorBitsPerSample.getString(0)
                cursorBitsPerSample.moveToNext()
            }
            cursorBitsPerSample.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorCompression.moveToFirst()
            allExifCompression = arrayOfNulls(cursorCompression.count)
            for (i in allExifCompression.indices) {
                allExifCompression[i] = cursorCompression.getString(0)
                cursorCompression.moveToNext()
            }
            cursorCompression.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorImageOrientation.moveToFirst()
            allExifImageOrientation = arrayOfNulls(cursorImageOrientation.count)
            for (i in allExifImageOrientation.indices) {
                allExifImageOrientation[i] = cursorImageOrientation.getString(0)
                cursorImageOrientation.moveToNext()
            }
            cursorImageOrientation.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorImageDescription.moveToFirst()
            allExifImageDescription = arrayOfNulls(cursorImageDescription.count)
            for (i in allExifImageDescription.indices) {
                allExifImageDescription[i] = cursorImageDescription.getString(0)
                cursorImageDescription.moveToNext()
            }
            cursorImageDescription.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorArtist.moveToFirst()
            allExifArtist = arrayOfNulls(cursorArtist.count)
            for (i in allExifArtist.indices) {
                allExifArtist[i] = cursorArtist.getString(0)
                cursorArtist.moveToNext()
            }
            cursorArtist.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorMaker.moveToFirst()
            allExifMaker = arrayOfNulls(cursorMaker.count)
            for (i in allExifMaker.indices) {
                allExifMaker[i] = cursorMaker.getString(0)
                cursorMaker.moveToNext()
            }
            cursorMaker.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorModel.moveToFirst()
            allExifModel = arrayOfNulls(cursorModel.count)
            for (i in allExifModel.indices) {
                allExifModel[i] = cursorModel.getString(0)
                cursorModel.moveToNext()
            }
            cursorModel.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorAperture.moveToFirst()
            allExifAperture = arrayOfNulls(cursorAperture.count)
            for (i in allExifAperture.indices) {
                allExifAperture[i] = cursorAperture.getString(0)
                cursorAperture.moveToNext()
            }
            cursorAperture.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorExposureTime.moveToFirst()
            allExifExposureTime = arrayOfNulls(cursorExposureTime.count)
            for (i in allExifExposureTime.indices) {
                allExifExposureTime[i] = cursorExposureTime.getString(0)
                cursorExposureTime.moveToNext()
            }
            cursorExposureTime.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorIsoSpeed.moveToFirst()
            allExifIsoSpeed = arrayOfNulls(cursorIsoSpeed.count)
            for (i in allExifIsoSpeed.indices) {
                allExifIsoSpeed[i] = cursorIsoSpeed.getString(0)
                cursorIsoSpeed.moveToNext()
            }
            cursorIsoSpeed.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorExposureBias.moveToFirst()
            allExifExposureBias = arrayOfNulls(cursorExposureBias.count)
            for (i in allExifExposureBias.indices) {
                allExifExposureBias[i] = cursorExposureBias.getString(0)
                cursorExposureBias.moveToNext()
            }
            cursorExposureBias.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorFNumber.moveToFirst()
            allExifFNumber = arrayOfNulls(cursorFNumber.count)
            for (i in allExifFNumber.indices) {
                allExifFNumber[i] = cursorFNumber.getString(0)
                cursorFNumber.moveToNext()
            }
            cursorFNumber.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorShutterSpeed.moveToFirst()
            allExifShutterSpeed = arrayOfNulls(cursorShutterSpeed.count)
            for (i in allExifShutterSpeed.indices) {
                allExifShutterSpeed[i] = cursorShutterSpeed.getString(0)
                cursorShutterSpeed.moveToNext()
            }
            cursorShutterSpeed.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorFocalLength.moveToFirst()
            allExifFocalLength = arrayOfNulls(cursorFocalLength.count)
            for (i in allExifFocalLength.indices) {
                allExifFocalLength[i] = cursorFocalLength.getString(0)
                cursorFocalLength.moveToNext()
            }
            cursorFocalLength.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorMeteringMode.moveToFirst()
            allExifMeteringMode = arrayOfNulls(cursorMeteringMode.count)
            for (i in allExifMeteringMode.indices) {
                allExifMeteringMode[i] = cursorMeteringMode.getString(0)
                cursorMeteringMode.moveToNext()
            }
            cursorMeteringMode.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorFlash.moveToFirst()
            allExifFlash = arrayOfNulls(cursorFlash.count)
            for (i in allExifFlash.indices) {
                allExifFlash[i] = cursorFlash.getString(0)
                cursorFlash.moveToNext()
            }
            cursorFlash.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorStripOffsets.moveToFirst()
            allExifStripOffsets = arrayOfNulls(cursorStripOffsets.count)
            for (i in allExifStripOffsets.indices) {
                allExifStripOffsets[i] = cursorStripOffsets.getString(0)
                cursorStripOffsets.moveToNext()
            }
            cursorStripOffsets.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorGpsVersionID.moveToFirst()
            allExifGpsVersionID = arrayOfNulls(cursorGpsVersionID.count)
            for (i in allExifGpsVersionID.indices) {
                allExifGpsVersionID[i] = cursorGpsVersionID.getString(0)
                cursorGpsVersionID.moveToNext()
            }
            cursorGpsVersionID.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorGpsLatitude.moveToFirst()
            allExifGpsLatitude = arrayOfNulls(cursorGpsLatitude.count)
            for (i in allExifGpsLatitude.indices) {
                allExifGpsLatitude[i] = cursorGpsLatitude.getString(0)
                cursorGpsLatitude.moveToNext()
            }
            cursorGpsLatitude.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorGpsLongitude.moveToFirst()
            allExifGpsLongitude = arrayOfNulls(cursorGpsLongitude.count)
            for (i in allExifGpsLongitude.indices) {
                allExifGpsLongitude[i] = cursorGpsLongitude.getString(0)
                cursorGpsLongitude.moveToNext()
            }
            cursorGpsLongitude.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorGpsAltitude.moveToFirst()
            allExifGpsAltitude = arrayOfNulls(cursorGpsAltitude.count)
            for (i in allExifGpsAltitude.indices) {
                allExifGpsAltitude[i] = cursorGpsAltitude.getString(0)
                cursorGpsAltitude.moveToNext()
            }
            cursorGpsAltitude.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorDateTimeOriginal.moveToFirst()
            allExifDateTimeOriginal = arrayOfNulls(cursorDateTimeOriginal.count)
            for (i in allExifDateTimeOriginal.indices) {
                allExifDateTimeOriginal[i] = cursorDateTimeOriginal.getString(0)
                cursorDateTimeOriginal.moveToNext()
            }
            cursorDateTimeOriginal.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            cursorChangeDateAndTime.moveToFirst()
            allExifChangeDateAndTime = arrayOfNulls(cursorChangeDateAndTime.count)
            for (i in allExifChangeDateAndTime.indices) {
                allExifChangeDateAndTime[i] = cursorChangeDateAndTime.getString(0)
                cursorChangeDateAndTime.moveToNext()
            }
            cursorChangeDateAndTime.close()
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
                allImageId[position],
                allImagePath[position],
                allImageName[position],
                allImageSentence1[position],
                allImageSentence2[position],
                allImageSentence3[position],
                allPhotoId[position],
                allExifImageLength[position],
                allExifImageWidth[position],
                allExifYResolution[position],
                allExifXResolution[position],
                allExifBitsPerSample[position],
                allExifCompression[position],
                allExifImageOrientation[position],
                allExifImageDescription[position],
                allExifArtist[position],
                allExifMaker[position],
                allExifModel[position],
                allExifAperture[position],
                allExifExposureTime[position],
                allExifIsoSpeed[position],
                allExifExposureBias[position],
                allExifFNumber[position],
                allExifShutterSpeed[position],
                allExifFocalLength[position],
                allExifMeteringMode[position],
                allExifFlash[position],
                allExifStripOffsets[position],
                allExifGpsVersionID[position],
                allExifGpsLatitude[position],
                allExifGpsLongitude[position],
                allExifGpsAltitude[position],
                allExifDateTimeOriginal[position],
                allExifChangeDateAndTime[position],
            )
    }


    // アプリバーの戻るボタンを押したときにfinish
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}