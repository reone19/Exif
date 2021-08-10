package com.example.exif

import android.content.ContentValues
import android.graphics.Color
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentExifBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_exif.*
import java.io.File
import java.io.IOException
import java.util.*

// viewPager2による更新で、フォト画面に行かないと表示されている値が更新されない問題解消のため
private var changeImageLength: Array<String?> = arrayOfNulls(allImageId.size)
private var changeImageWidth: Array<String?> = arrayOfNulls(allImageId.size)
private var changeYResolution: Array<String?> = arrayOfNulls(allImageId.size)
private var changeXResolution: Array<String?> = arrayOfNulls(allImageId.size)
private var changeBitsPerSample: Array<String?> = arrayOfNulls(allImageId.size)
private var changeCompression: Array<String?> = arrayOfNulls(allImageId.size)
private var changeImageOrientation: Array<String?> = arrayOfNulls(allImageId.size)
private var changeImageDescription: Array<String?> = arrayOfNulls(allImageId.size)
private var changeArtist: Array<String?> = arrayOfNulls(allImageId.size)
private var changeMaker: Array<String?> = arrayOfNulls(allImageId.size)
private var changeModel: Array<String?> = arrayOfNulls(allImageId.size)
private var changeAperture: Array<String?> = arrayOfNulls(allImageId.size)
private var changeExposureTime: Array<String?> = arrayOfNulls(allImageId.size)
private var changeIsoSpeed: Array<String?> = arrayOfNulls(allImageId.size)
private var changeExposureBias: Array<String?> = arrayOfNulls(allImageId.size)
private var changeFNumber: Array<String?> = arrayOfNulls(allImageId.size)
private var changeShutterSpeed: Array<String?> = arrayOfNulls(allImageId.size)
private var changeFocalLength: Array<String?> = arrayOfNulls(allImageId.size)
private var changeMeteringMode: Array<String?> = arrayOfNulls(allImageId.size)
private var changeFlash: Array<String?> = arrayOfNulls(allImageId.size)
private var changeStripOffsets: Array<String?> = arrayOfNulls(allImageId.size)
private var changeGpsVersionID: Array<String?> = arrayOfNulls(allImageId.size)
private var changeGpsLatitude: Array<String?> = arrayOfNulls(allImageId.size)
private var changeGpsLongitude: Array<String?> = arrayOfNulls(allImageId.size)
private var changeGpsAltitude: Array<String?> = arrayOfNulls(allImageId.size)
private var changeDateTimeOriginal: Array<String?> = arrayOfNulls(allImageId.size)
private var changeChangeDateAndTime: Array<String?> = arrayOfNulls(allImageId.size)

private var changeExifFlag: Array<String?> = arrayOfNulls(allImageId.size)


class ExifFragment : Fragment() {

    // バインディング
    private var _binding: FragmentExifBinding? = null
    private val binding get() = _binding!!

    private val arrayListPhotoId: ArrayList<String> = arrayListOf()
    private val arrayListImageName: ArrayList<String> = arrayListOf()
    private val arrayListImageLength: ArrayList<String> = arrayListOf()
    private val arrayListImageWidth: ArrayList<String> = arrayListOf()
    private val arrayListYResolution: ArrayList<String> = arrayListOf()
    private val arrayListXResolution: ArrayList<String> = arrayListOf()
    private val arrayListBitsPerSample: ArrayList<String> = arrayListOf()
    private val arrayListCompression: ArrayList<String> = arrayListOf()
    private val arrayListImageOrientation: ArrayList<String> = arrayListOf()
    private val arrayListImageDescription: ArrayList<String> = arrayListOf()
    private val arrayListArtist: ArrayList<String> = arrayListOf()
    private val arrayListMaker: ArrayList<String> = arrayListOf()
    private val arrayListModel: ArrayList<String> = arrayListOf()
    private val arrayListAperture: ArrayList<String> = arrayListOf()
    private val arrayListExposureTime: ArrayList<String> = arrayListOf()
    private val arrayListIsoSpeed: ArrayList<String> = arrayListOf()
    private val arrayListExposureBias: ArrayList<String> = arrayListOf()
    private val arrayListFNumber: ArrayList<String> = arrayListOf()
    private val arrayListShutterSpeed: ArrayList<String> = arrayListOf()
    private val arrayListFocalLength: ArrayList<String> = arrayListOf()
    private val arrayListMeteringMode: ArrayList<String> = arrayListOf()
    private val arrayListFlash: ArrayList<String> = arrayListOf()
    private val arrayListStripOffsets: ArrayList<String> = arrayListOf()
    private val arrayListGpsVersionID: ArrayList<String> = arrayListOf()
    private val arrayListGpsLatitude: ArrayList<String> = arrayListOf()
    private val arrayListGpsLongitude: ArrayList<String> = arrayListOf()
    private val arrayListGpsAltitude: ArrayList<String> = arrayListOf()
    private val arrayListDateTimeOriginal: ArrayList<String> = arrayListOf()
    private val arrayListChangeDateAndTime: ArrayList<String> = arrayListOf()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExifBinding.inflate(inflater, container, false)


        //データベース接続
        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)

        // データの取得処理
        val databaseR = dbHelper.readableDatabase
        val sql =
            "SELECT photo_id, image_name, image_length, image_width, y_resolution, x_resolution, bits_per_sample, compression, image_orientation, image_description, artist, maker, model, aperture, exposure_time, iso_speed, exposure_bias, f_number, shutter_speed, focal_length, metering_mode, flash, strip_offsets, gps_version_id, gps_latitude, gps_longitude, gps_altitude, date_time_original, change_date_and_time FROM Meta WHERE photo_id = $photoID"
        val cursor = databaseR.rawQuery(sql, null)

        if (cursor.count > 0) {
            cursor.moveToFirst()

            while (!cursor.isAfterLast) {

                arrayListPhotoId.add(cursor.getString(0))
                arrayListImageName.add(cursor.getString(1))
                arrayListImageLength.add(cursor.getString(2))
                arrayListImageWidth.add(cursor.getString(3))
                arrayListYResolution.add(cursor.getString(4))
                arrayListXResolution.add(cursor.getString(5))
                arrayListBitsPerSample.add(cursor.getString(6))
                arrayListCompression.add(cursor.getString(7))
                arrayListImageOrientation.add(cursor.getString(8))
                arrayListImageDescription.add(cursor.getString(9))
                arrayListArtist.add(cursor.getString(10))
                arrayListMaker.add(cursor.getString(11))
                arrayListModel.add(cursor.getString(12))
                arrayListAperture.add(cursor.getString(13))
                arrayListExposureTime.add(cursor.getString(14))
                arrayListIsoSpeed.add(cursor.getString(15))
                arrayListExposureBias.add(cursor.getString(16))
                arrayListFNumber.add(cursor.getString(17))
                arrayListShutterSpeed.add(cursor.getString(18))
                arrayListFocalLength.add(cursor.getString(19))
                arrayListMeteringMode.add(cursor.getString(20))
                arrayListFlash.add(cursor.getString(21))
                arrayListStripOffsets.add(cursor.getString(22))
                arrayListGpsVersionID.add(cursor.getString(23))
                arrayListGpsLatitude.add(cursor.getString(24))
                arrayListGpsLongitude.add(cursor.getString(25))
                arrayListGpsAltitude.add(cursor.getString(26))
                arrayListDateTimeOriginal.add(cursor.getString(27))
                arrayListChangeDateAndTime.add(cursor.getString(28))

                cursor.moveToNext()
            }
        }


        // 画像の高さ
        var imageLength: String? = null
        // 画像の横幅
        var imageWidth: String? = null
        // 画像の高さの解像度
        var yResolution: String? = null
        // 画像の横幅の解像度
        var xResolution: String? = null
        // 画像のビットの深さ
        var bitsPerSample: String? = null
        // 圧縮の種類
        var compression: String? = null
        // 画像方向
        var imageOrientation: String? = null
        // 画像タイトル
        var imageDescription: String? = null
        // 作者名
        var artist: String? = null
        // メーカ名
        var maker: String? = null
        //モデル名
        var model: String? = null
        // 絞り値
        var aperture: String? = null
        // 露出時間
        var exposureTime: String? = null
        // ISO値
        var isoSpeed: String? = null
        // 露出補正時間
        var exposureBias: String? = null
        // F値
        var fNumber: String? = null
        // シャッタースピード
        var shutterSpeed: String? = null
        // 焦点距離
        var focalLength: String? = null
        // 測光モード
        var meteringMode: String? = null
        // フラッシュ
        var flash: String? = null
        // ロケーション
        var stripOffsets: String? = null
        // GPSタグのバージョン
        var gpsVersionID: String? = null
        // 経度
        var gpsLatitude: String? = null
        // 緯度
        var gpsLongitude: String? = null
        // 高度
        var gpsAltitude: String? = null
        // 原画像データの生成日時
        var dateTimeOriginal: String? = null
        // 更新日時
        var changeDateAndTime: String? = null


        try {
            // 画像の高さ
            imageLength = arrayListImageLength[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 画像の横幅
            imageWidth = arrayListImageWidth[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 画像の高さの解像度
            yResolution = arrayListYResolution[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 画像の横幅の解像度
            xResolution = arrayListXResolution[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 画像のビットの深さ
            bitsPerSample = arrayListBitsPerSample[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 圧縮の種類
            compression = arrayListCompression[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 画像方向
            imageOrientation = arrayListImageOrientation[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 画像タイトル
            imageDescription = arrayListImageDescription[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 作者名
            artist = arrayListArtist[0]
        } catch (e: NullPointerException) {
        }

        try {
            // メーカ名
            maker = arrayListMaker[0]
        } catch (e: NullPointerException) {
        }

        try {
            // モデル名
            model = arrayListModel[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 絞り値
            aperture = arrayListAperture[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 露出時間
            exposureTime = arrayListExposureTime[0]
        } catch (e: NullPointerException) {
        }

        try {
            // ISO値
            isoSpeed = arrayListIsoSpeed[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 露出補正時間
            exposureBias = arrayListExposureBias[0]
        } catch (e: NullPointerException) {
        }

        try {
            // F値
            fNumber = arrayListFNumber[0]
        } catch (e: NullPointerException) {
        }

        try {
            // シャッタースピード
            shutterSpeed = arrayListShutterSpeed[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 焦点距離
            focalLength = arrayListFocalLength[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 測光モード
            meteringMode = arrayListMeteringMode[0]
        } catch (e: NullPointerException) {
        }

        try {
            // フラッシュ
            flash = arrayListFlash[0]
        } catch (e: NullPointerException) {
        }

        try {
            // ロケーション
            stripOffsets = arrayListStripOffsets[0]
        } catch (e: NullPointerException) {
        }

        try {
            // GPSタグのバージョン
            gpsVersionID = arrayListGpsVersionID[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 経度
            gpsLatitude = arrayListGpsLatitude[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 緯度
            gpsLongitude = arrayListGpsLongitude[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 高度
            gpsAltitude = arrayListGpsAltitude[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 原画像データの生成日時
            dateTimeOriginal = arrayListDateTimeOriginal[0]
        } catch (e: NullPointerException) {
        }

        try {
            // 更新日時
            changeDateAndTime = arrayListChangeDateAndTime[0]
        } catch (e: NullPointerException) {
        }

        // データセット(viewPager2からセット)
        if (changeExifFlag[imageResId!! - 1] == true.toString()) {
            binding.imageLength.setText(changeImageLength[imageResId!! - 1])
            binding.imageWidth.setText(changeImageWidth[imageResId!! - 1])
            binding.yResolution.setText(changeYResolution[imageResId!! - 1])
            binding.xResolution.setText(changeXResolution[imageResId!! - 1])
            binding.bitsPerSample.setText(changeBitsPerSample[imageResId!! - 1])
            binding.compression.setText(changeCompression[imageResId!! - 1])
            binding.imageOrientation.setText(changeImageOrientation[imageResId!! - 1])
            binding.imageDescription.setText(changeImageDescription[imageResId!! - 1])
            binding.artist.setText(changeArtist[imageResId!! - 1])
            binding.maker.setText(changeMaker[imageResId!! - 1])
            binding.model.setText(changeModel[imageResId!! - 1])
            binding.aperture.setText(changeAperture[imageResId!! - 1])
            binding.exposureTime.setText(changeExposureTime[imageResId!! - 1])
            binding.isoSpeed.setText(changeIsoSpeed[imageResId!! - 1])
            binding.exposureBias.setText(changeExposureBias[imageResId!! - 1])
            binding.fNumber.setText(changeFNumber[imageResId!! - 1])
            binding.shutterSpeed.setText(changeShutterSpeed[imageResId!! - 1])
            binding.focalLength.setText(changeFocalLength[imageResId!! - 1])
            binding.meteringMode.setText(changeMeteringMode[imageResId!! - 1])
            binding.flash.setText(changeFlash[imageResId!! - 1])
            binding.stripOffsets.setText(changeStripOffsets[imageResId!! - 1])
            binding.gpsVersionID.setText(changeGpsVersionID[imageResId!! - 1])
            binding.gpsLatitude.setText(changeGpsLatitude[imageResId!! - 1])
            binding.gpsLongitude.setText(changeGpsLongitude[imageResId!! - 1])
            binding.gpsAltitude.setText(changeGpsAltitude[imageResId!! - 1])
            binding.dateTimeOriginal.setText(changeDateTimeOriginal[imageResId!! - 1])
            binding.changeDateAndTime.setText(changeChangeDateAndTime[imageResId!! - 1])
        } else {
            binding.imageLength.setText(imageResImageLength)
            binding.imageWidth.setText(imageResImageWidth)
            binding.yResolution.setText(imageResYResolution)
            binding.xResolution.setText(imageResXResolution)
            binding.bitsPerSample.setText(imageResBitsPerSample)
            binding.compression.setText(imageResCompression)
            binding.imageOrientation.setText(imageResImageOrientation)
            binding.imageDescription.setText(imageResImageDescription)
            binding.artist.setText(imageResArtist)
            binding.maker.setText(imageResMaker)
            binding.model.setText(imageResModel)
            binding.aperture.setText(imageResAperture)
            binding.exposureTime.setText(imageResExposureTime)
            binding.isoSpeed.setText(imageResIsoSpeed)
            binding.exposureBias.setText(imageResExposureBias)
            binding.fNumber.setText(imageResFNumber)
            binding.shutterSpeed.setText(imageResShutterSpeed)
            binding.focalLength.setText(imageResFocalLength)
            binding.meteringMode.setText(imageResMeteringMode)
            binding.flash.setText(imageResFlash)
            binding.stripOffsets.setText(imageResStripOffsets)
            binding.gpsVersionID.setText(imageResGpsVersionID)
            binding.gpsLatitude.setText(imageResGpsLatitude)
            binding.gpsLongitude.setText(imageResGpsLongitude)
            binding.gpsAltitude.setText(imageResGpsAltitude)
            binding.dateTimeOriginal.setText(imageResDateTimeOriginal)
            binding.changeDateAndTime.setText(imageResChangeDateAndTime)
        }


        // 保存ボタンを押したときの動作
        binding.exifSave.setOnClickListener {
            updateExif()
        }


        // 一括削除ボタンを押したときの動作　→　確認ダイアログを表示
        binding.exifAllDelete.setOnClickListener {
            val dialog = ExifDeleteDialog(
                "削除しますか？",

                "削除", {
                    allDeleteExif()
                    Snackbar.make(it, "削除しました", Snackbar.LENGTH_SHORT)
                        .setAction("戻る") { activity?.finish() }
                        .setActionTextColor(Color.YELLOW)
                        .show()
                },

                "キャンセル", { }
            )
            dialog.show(parentFragmentManager, "exif_delete_dialog")
        }

        return binding.root
    }


    // 保存ボタンを押したときの動作
    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateExif() {

        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
        // Exif用imagePath
        val f: File = File(imagePath)
        val uri = Uri.fromFile(f)

        // 各ExifのEditTextを変数に取得
        val imageLength = view?.findViewById<EditText>(R.id.imageLength)
        val imageWidth = view?.findViewById<EditText>(R.id.imageWidth)
        val yResolution = view?.findViewById<EditText>(R.id.yResolution)
        val xResolution = view?.findViewById<EditText>(R.id.xResolution)
        val bitsPerSample = view?.findViewById<EditText>(R.id.bitsPerSample)
        val compression = view?.findViewById<EditText>(R.id.compression)
        val imageOrientation = view?.findViewById<EditText>(R.id.imageOrientation)
        val imageDescription = view?.findViewById<EditText>(R.id.imageDescription)
        val artist = view?.findViewById<EditText>(R.id.artist)
        val maker = view?.findViewById<EditText>(R.id.maker)
        val model = view?.findViewById<EditText>(R.id.model)
        val aperture = view?.findViewById<EditText>(R.id.aperture)
        val exposureTime = view?.findViewById<EditText>(R.id.exposureTime)
        val isoSpeed = view?.findViewById<EditText>(R.id.isoSpeed)
        val exposureBias = view?.findViewById<EditText>(R.id.exposureBias)
        val fNumber = view?.findViewById<EditText>(R.id.fNumber)
        val shutterSpeed = view?.findViewById<EditText>(R.id.shutterSpeed)
        val focalLength = view?.findViewById<EditText>(R.id.focalLength)
        val meteringMode = view?.findViewById<EditText>(R.id.meteringMode)
        val flash = view?.findViewById<EditText>(R.id.flash)
        val stripOffsets = view?.findViewById<EditText>(R.id.stripOffsets)
        val gpsVersionID = view?.findViewById<EditText>(R.id.gpsVersionID)
        val gpsLatitude = view?.findViewById<EditText>(R.id.gpsLatitude)
        val gpsLongitude = view?.findViewById<EditText>(R.id.gpsLongitude)
        val gpsAltitude = view?.findViewById<EditText>(R.id.gpsAltitude)
        val dateTimeOriginal = view?.findViewById<EditText>(R.id.dateTimeOriginal)
        val changeDateAndTime = view?.findViewById<EditText>(R.id.changeDateAndTime)

        // Exif直接編集処理
        try {
            activity?.contentResolver?.openInputStream(uri)?.use {
                val exifInterface = ExifInterface(
                    context?.contentResolver?.openFileDescriptor(
                        uri,
                        "rw",
                        null
                    )!!.fileDescriptor
                )

                // EditTextの値をExifにセット
                // 空文字のときはnullをセット
                if (imageLength?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_IMAGE_LENGTH,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_IMAGE_LENGTH,
                        imageLength?.text.toString()
                    )
                }

                if (imageWidth?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_IMAGE_WIDTH,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_IMAGE_WIDTH,
                        imageWidth?.text.toString()
                    )
                }

                if (yResolution?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_Y_RESOLUTION,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_Y_RESOLUTION,
                        yResolution?.text.toString()
                    )
                }

                if (xResolution?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_X_RESOLUTION,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_X_RESOLUTION,
                        xResolution?.text.toString()
                    )
                }

                if (bitsPerSample?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_BITS_PER_SAMPLE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_BITS_PER_SAMPLE,
                        bitsPerSample?.text.toString()
                    )
                }

                if (compression?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_COMPRESSION,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_COMPRESSION,
                        compression?.text.toString()
                    )
                }

                if (imageOrientation?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_ORIENTATION,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_ORIENTATION,
                        imageOrientation?.text.toString()
                    )
                }

                if (imageDescription?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_IMAGE_DESCRIPTION,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_IMAGE_DESCRIPTION,
                        imageDescription?.text.toString()
                    )
                }

                if (artist?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_ARTIST,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_ARTIST,
                        artist?.text.toString()
                    )
                }

                if (maker?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_MAKE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_MAKE,
                        maker?.text.toString()
                    )
                }

                if (model?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_MODEL,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_MODEL,
                        model?.text.toString()
                    )
                }

                if (aperture?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_APERTURE_VALUE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_APERTURE_VALUE,
                        aperture?.text.toString()
                    )
                }

                if (exposureTime?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_EXPOSURE_TIME,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_EXPOSURE_TIME,
                        exposureTime?.text.toString()
                    )
                }

                if (isoSpeed?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_ISO_SPEED_RATINGS,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_ISO_SPEED_RATINGS,
                        isoSpeed?.text.toString()
                    )
                }

                if (exposureBias?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_EXPOSURE_BIAS_VALUE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_EXPOSURE_BIAS_VALUE,
                        exposureBias?.text.toString()
                    )
                }

                if (fNumber?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_F_NUMBER,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_F_NUMBER,
                        fNumber?.text.toString()
                    )
                }

                if (shutterSpeed?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_SHUTTER_SPEED_VALUE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_SHUTTER_SPEED_VALUE,
                        shutterSpeed?.text.toString()
                    )
                }

                if (focalLength?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_FOCAL_LENGTH,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_FOCAL_LENGTH,
                        focalLength?.text.toString()
                    )
                }

                if (meteringMode?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_METERING_MODE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_METERING_MODE,
                        meteringMode?.text.toString()
                    )
                }

                if (flash?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_FLASH,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_FLASH,
                        flash?.text.toString()
                    )
                }

                if (stripOffsets?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_STRIP_OFFSETS,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_STRIP_OFFSETS,
                        stripOffsets?.text.toString()
                    )
                }

                if (gpsVersionID?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_GPS_VERSION_ID,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_GPS_VERSION_ID,
                        gpsVersionID?.text.toString()
                    )
                }

                if (gpsLatitude?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_GPS_LATITUDE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_GPS_LATITUDE,
                        gpsLatitude?.text.toString()
                    )
                }

                if (gpsLongitude?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_GPS_LONGITUDE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_GPS_LONGITUDE,
                        gpsLongitude?.text.toString()
                    )
                }

                if (gpsAltitude?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_GPS_ALTITUDE,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_GPS_ALTITUDE,
                        gpsAltitude?.text.toString()
                    )
                }

                if (dateTimeOriginal?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_DATETIME_ORIGINAL,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_DATETIME_ORIGINAL,
                        dateTimeOriginal?.text.toString()
                    )
                }

                if (changeDateAndTime?.text.toString().isEmpty()) {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_DATETIME,
                        null
                    )
                } else {
                    exifInterface.setAttribute(
                        ExifInterface.TAG_DATETIME,
                        changeDateAndTime?.text.toString()
                    )
                }

                // Exifを更新
                exifInterface.saveAttributes()
            }

        } catch (e: IOException) {
            e.stackTrace
            e.message?.let { Log.e("ExifActivity", it) }
        }

        // データベース更新処理
        try {
            // 前処理
            val database = dbHelper.writableDatabase
            val values = ContentValues()

            // 各カラムの値をデータベースにセット
            // EditTextの値が空文字の場合、removeすることによりnullをセットする
            if (imageLength?.text.toString().isEmpty()) {
                values.putNull("image_length")
            } else {
                values.put("image_length", imageLength?.text.toString())
            }

            if (imageWidth?.text.toString().isEmpty()) {
                values.putNull("image_width")
            } else {
                values.put("image_width", imageWidth?.text.toString())
            }

            if (yResolution?.text.toString().isEmpty()) {
                values.putNull("y_resolution")
            } else {
                values.put("y_resolution", yResolution?.text.toString())
            }

            if (xResolution?.text.toString().isEmpty()) {
                values.putNull("x_resolution")
            } else {
                values.put("x_resolution", xResolution?.text.toString())
            }

            if (bitsPerSample?.text.toString().isEmpty()) {
                values.putNull("bits_per_sample")
            } else {
                values.put("bits_per_sample", bitsPerSample?.text.toString())
            }

            if (compression?.text.toString().isEmpty()) {
                values.putNull("compression")
            } else {
                values.put("compression", compression?.text.toString())
            }

            if (imageOrientation?.text.toString().isEmpty()) {
                values.putNull("image_orientation")
            } else {
                values.put("image_orientation", imageOrientation?.text.toString())
            }

            if (imageDescription?.text.toString().isEmpty()) {
                values.putNull("image_description")
            } else {
                values.put("image_description", imageDescription?.text.toString())
            }

            if (artist?.text.toString().isEmpty()) {
                values.putNull("artist")
            } else {
                values.put("artist", artist?.text.toString())
            }

            if (maker?.text.toString().isEmpty()) {
                values.putNull("maker")
            } else {
                values.put("maker", maker?.text.toString())
            }

            if (model?.text.toString().isEmpty()) {
                values.putNull("model")
            } else {
                values.put("model", model?.text.toString())
            }

            if (aperture?.text.toString().isEmpty()) {
                values.putNull("aperture")
            } else {
                values.put("aperture", aperture?.text.toString())
            }

            if (exposureTime?.text.toString().isEmpty()) {
                values.putNull("exposure_time")
            } else {
                values.put("exposure_time", exposureTime?.text.toString())
            }

            if (isoSpeed?.text.toString().isEmpty()) {
                values.putNull("iso_speed")
            } else {
                values.put("iso_speed", isoSpeed?.text.toString())
            }

            if (exposureBias?.text.toString().isEmpty()) {
                values.putNull("exposure_bias")
            } else {
                values.put("exposure_bias", exposureBias?.text.toString())
            }

            if (fNumber?.text.toString().isEmpty()) {
                values.putNull("f_number")
            } else {
                values.put("f_number", fNumber?.text.toString())
            }

            if (shutterSpeed?.text.toString().isEmpty()) {
                values.putNull("shutter_speed")
            } else {
                values.put("shutter_speed", shutterSpeed?.text.toString())
            }

            if (focalLength?.text.toString().isEmpty()) {
                values.putNull("focal_length")
            } else {
                values.put("focal_length", focalLength?.text.toString())
            }

            if (meteringMode?.text.toString().isEmpty()) {
                values.putNull("metering_mode")
            } else {
                values.put("metering_mode", meteringMode?.text.toString())
            }

            if (flash?.text.toString().isEmpty()) {
                values.putNull("flash")
            } else {
                values.put("flash", flash?.text.toString())
            }

            if (stripOffsets?.text.toString().isEmpty()) {
                values.putNull("strip_offsets")
            } else {
                values.put("strip_offsets", stripOffsets?.text.toString())
            }

            if (gpsVersionID?.text.toString().isEmpty()) {
                values.putNull("gps_version_id")
            } else {
                values.put("gps_version_id", gpsVersionID?.text.toString())
            }

            if (gpsLatitude?.text.toString().isEmpty()) {
                values.putNull("gps_latitude")
            } else {
                values.put("gps_latitude", gpsLatitude?.text.toString())
            }

            if (gpsLongitude?.text.toString().isEmpty()) {
                values.putNull("gps_longitude")
            } else {
                values.put("gps_longitude", gpsLongitude?.text.toString())
            }

            if (gpsAltitude?.text.toString().isEmpty()) {
                values.putNull("gps_altitude")
            } else {
                values.put("gps_altitude", gpsAltitude?.text.toString())
            }

            if (dateTimeOriginal?.text.toString().isEmpty()) {
                values.putNull("date_time_original")
            } else {
                values.put("date_time_original", dateTimeOriginal?.text.toString())
            }

            if (changeDateAndTime?.text.toString().isEmpty()) {
                values.putNull("change_date_and_time")
            } else {
                values.put("change_date_and_time", changeDateAndTime?.text.toString())
            }

            // 一括でMetaテーブルをアップデート
            database.update("Meta", values, "photo_id=$imageResPhotoId", null)

            //viewPager2のため、値を保持
            changeImageLength[imageResId!! - 1] = imageLength?.text.toString()
            changeImageWidth[imageResId!! - 1] = imageWidth?.text.toString()
            changeYResolution[imageResId!! - 1] = yResolution?.text.toString()
            changeXResolution[imageResId!! - 1] = xResolution?.text.toString()
            changeBitsPerSample[imageResId!! - 1] = bitsPerSample?.text.toString()
            changeCompression[imageResId!! - 1] = compression?.text.toString()
            changeImageOrientation[imageResId!! - 1] = imageOrientation?.text.toString()
            changeImageDescription[imageResId!! - 1] = imageDescription?.text.toString()
            changeArtist[imageResId!! - 1] = artist?.text.toString()
            changeMaker[imageResId!! - 1] = maker?.text.toString()
            changeModel[imageResId!! - 1] = model?.text.toString()
            changeAperture[imageResId!! - 1] = aperture?.text.toString()
            changeExposureTime[imageResId!! - 1] = exposureTime?.text.toString()
            changeIsoSpeed[imageResId!! - 1] = isoSpeed?.text.toString()
            changeExposureBias[imageResId!! - 1] = exposureBias?.text.toString()
            changeFNumber[imageResId!! - 1] = fNumber?.text.toString()
            changeShutterSpeed[imageResId!! - 1] = shutterSpeed?.text.toString()
            changeFocalLength[imageResId!! - 1] = focalLength?.text.toString()
            changeMeteringMode[imageResId!! - 1] = meteringMode?.text.toString()
            changeFlash[imageResId!! - 1] = flash?.text.toString()
            changeStripOffsets[imageResId!! - 1] = stripOffsets?.text.toString()
            changeGpsVersionID[imageResId!! - 1] = gpsVersionID?.text.toString()
            changeGpsLatitude[imageResId!! - 1] = gpsLatitude?.text.toString()
            changeGpsLongitude[imageResId!! - 1] = gpsLongitude?.text.toString()
            changeGpsAltitude[imageResId!! - 1] = gpsAltitude?.text.toString()
            changeDateTimeOriginal[imageResId!! - 1] = dateTimeOriginal?.text.toString()
            changeChangeDateAndTime[imageResId!! - 1] = changeDateAndTime?.text.toString()

            changeExifFlag[imageResId!! - 1] = true.toString()

            // スナックバー表示
            view?.let {
                Snackbar.make(it, "保存しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") { activity?.finish() }
                    .setActionTextColor(Color.YELLOW)
                    .show()
            }

        } catch (exception: Exception) {
            Log.e("updateData", exception.toString())
        }

    }

    // 確認ダイアログにて、削除ボタンを押したときの動作
    @RequiresApi(Build.VERSION_CODES.N)
    private fun allDeleteExif() {

        val dbHelper = SampleDBHelper(requireContext(), "SampleDB", null, 1)
        // Exif用imagePath
        val f: File = File(imagePath)
        val uri = Uri.fromFile(f)

        // Exif直接編集処理
        try {
            activity?.contentResolver?.openInputStream(uri)?.use {
                val exifInterface = ExifInterface(
                    context?.contentResolver?.openFileDescriptor(
                        uri,
                        "rw",
                        null
                    )!!.fileDescriptor
                )

                // nullをセット
                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_LENGTH,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_WIDTH,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_Y_RESOLUTION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_X_RESOLUTION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_BITS_PER_SAMPLE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_COMPRESSION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ORIENTATION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_IMAGE_DESCRIPTION,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ARTIST,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_MAKE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_MODEL,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_APERTURE_VALUE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_EXPOSURE_TIME,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_ISO_SPEED_RATINGS,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_EXPOSURE_BIAS_VALUE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_F_NUMBER,
                    null
                )


                exifInterface.setAttribute(
                    ExifInterface.TAG_SHUTTER_SPEED_VALUE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_FOCAL_LENGTH,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_METERING_MODE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_FLASH,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_STRIP_OFFSETS,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_VERSION_ID,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_LATITUDE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_LONGITUDE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_GPS_ALTITUDE,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_DATETIME_ORIGINAL,
                    null
                )

                exifInterface.setAttribute(
                    ExifInterface.TAG_DATETIME,
                    null
                )

                // Exifを更新
                exifInterface.saveAttributes()
            }

        } catch (e: IOException) {
            e.stackTrace
            e.message?.let { Log.e("ExifActivity", it) }
        }

        // データベース更新処理
        try {
            // 前処理
            val database = dbHelper.writableDatabase
            val values = ContentValues()

            // nullをセット
            values.putNull("image_length")
            values.putNull("image_width")
            values.putNull("y_resolution")
            values.putNull("x_resolution")
            values.putNull("bits_per_sample")
            values.putNull("compression")
            values.putNull("image_orientation")
            values.putNull("image_description")
            values.putNull("artist")
            values.putNull("maker")
            values.putNull("model")
            values.putNull("aperture")
            values.putNull("exposure_time")
            values.putNull("iso_speed")
            values.putNull("exposure_bias")
            values.putNull("f_number")
            values.putNull("shutter_speed")
            values.putNull("focal_length")
            values.putNull("metering_mode")
            values.putNull("flash")
            values.putNull("strip_offsets")
            values.putNull("gps_version_id")
            values.putNull("gps_latitude")
            values.putNull("gps_longitude")
            values.putNull("gps_altitude")
            values.putNull("date_time_original")
            values.putNull("change_date_and_time")

            // 一括でMetaテーブルをアップデート
            database.update("Meta", values, "photo_id=$imageResPhotoId", null)

            // EditTextの表示を削除
            binding.imageLength.text = null
            binding.imageWidth.text = null
            binding.yResolution.text = null
            binding.xResolution.text = null
            binding.bitsPerSample.text = null
            binding.compression.text = null
            binding.imageOrientation.text = null
            binding.imageDescription.text = null
            binding.artist.text = null
            binding.maker.text = null
            binding.model.text = null
            binding.aperture.text = null
            binding.exposureTime.text = null
            binding.isoSpeed.text = null
            binding.exposureBias.text = null
            binding.fNumber.text = null
            binding.shutterSpeed.text = null
            binding.focalLength.text = null
            binding.meteringMode.text = null
            binding.flash.text = null
            binding.stripOffsets.text = null
            binding.gpsVersionID.text = null
            binding.gpsLatitude.text = null
            binding.gpsLongitude.text = null
            binding.gpsAltitude.text = null
            binding.dateTimeOriginal.text = null
            binding.changeDateAndTime.text = null

            //viewPager2のため、値を保持
            changeImageLength[imageResId!! - 1] = imageLength?.text.toString()
            changeImageWidth[imageResId!! - 1] = imageWidth?.text.toString()
            changeYResolution[imageResId!! - 1] = yResolution?.text.toString()
            changeXResolution[imageResId!! - 1] = xResolution?.text.toString()
            changeBitsPerSample[imageResId!! - 1] = bitsPerSample?.text.toString()
            changeCompression[imageResId!! - 1] = compression?.text.toString()
            changeImageOrientation[imageResId!! - 1] = imageOrientation?.text.toString()
            changeImageDescription[imageResId!! - 1] = imageDescription?.text.toString()
            changeArtist[imageResId!! - 1] = artist?.text.toString()
            changeMaker[imageResId!! - 1] = maker?.text.toString()
            changeModel[imageResId!! - 1] = model?.text.toString()
            changeAperture[imageResId!! - 1] = aperture?.text.toString()
            changeExposureTime[imageResId!! - 1] = exposureTime?.text.toString()
            changeIsoSpeed[imageResId!! - 1] = isoSpeed?.text.toString()
            changeExposureBias[imageResId!! - 1] = exposureBias?.text.toString()
            changeFNumber[imageResId!! - 1] = fNumber?.text.toString()
            changeShutterSpeed[imageResId!! - 1] = shutterSpeed?.text.toString()
            changeFocalLength[imageResId!! - 1] = focalLength?.text.toString()
            changeMeteringMode[imageResId!! - 1] = meteringMode?.text.toString()
            changeFlash[imageResId!! - 1] = flash?.text.toString()
            changeStripOffsets[imageResId!! - 1] = stripOffsets?.text.toString()
            changeGpsVersionID[imageResId!! - 1] = gpsVersionID?.text.toString()
            changeGpsLatitude[imageResId!! - 1] = gpsLatitude?.text.toString()
            changeGpsLongitude[imageResId!! - 1] = gpsLongitude?.text.toString()
            changeGpsAltitude[imageResId!! - 1] = gpsAltitude?.text.toString()
            changeDateTimeOriginal[imageResId!! - 1] = dateTimeOriginal?.text.toString()
            changeChangeDateAndTime[imageResId!! - 1] = changeDateAndTime?.text.toString()

            changeExifFlag[imageResId!! - 1] = true.toString()

        } catch (exception: Exception) {
            Log.e("deleteData", exception.toString())
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}