package com.example.exif

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentPhotoDetailBinding
import java.io.File

// ID
private const val IMG_RES_ID = "IMG_RES_ID"
private const val IMG_RES_PATH = "IMG_RES_PATH"
private const val IMG_RES_NAME = "IMG_RES_NAME"
private const val IMG_RES_SENTENCE1 = "IMG_RES_SENTENCE1"
private const val IMG_RES_SENTENCE2 = "IMG_RES_SENTENCE2"
private const val IMG_RES_SENTENCE3 = "IMG_RES_SENTENCE3"
private const val IMG_RES_PHOTO_ID = "IMG_RES_PHOTO_ID"
private const val IMG_RES_IMAGE_LENGTH = "IMG_RES_IMAGE_LENGTH"
private const val IMG_RES_IMAGE_WIDTH = "IMG_RES_IMAGE_WIDTH"
private const val IMG_RES_Y_RESOLUTION = "IMG_RES_Y_RESOLUTION"
private const val IMG_RES_X_RESOLUTION = "IMG_RES_X_RESOLUTION"
private const val IMG_RES_BITS_PER_SAMPLE = "IMG_RES_BITS_PER_SAMPLE"
private const val IMG_RES_COMPRESSION = "IMG_RES_COMPRESSION"
private const val IMG_RES_IMAGE_ORIENTATION = "IMG_RES_IMAGE_ORIENTATION"
private const val IMG_RES_IMAGE_DESCRIPTION = "IMG_RES_IMAGE_DESCRIPTION"
private const val IMG_RES_ARTIST = "IMG_RES_ARTIST"
private const val IMG_RES_MAKER = "IMG_RES_MAKER"
private const val IMG_RES_MODEL = "IMG_RES_MODEL"
private const val IMG_RES_APERTURE = "IMG_RES_APERTURE"
private const val IMG_RES_EXPOSURE_TIME = "IMG_RES_EXPOSURE_TIME"
private const val IMG_RES_ISO_SPEED = "IMG_RES_ISO_SPEED"
private const val IMG_RES_EXPOSURE_BIAS = "IMG_RES_IMAGE_BIAS"
private const val IMG_RES_F_NUMBER = "IMG_RES_F_NUMBER"
private const val IMG_RES_SHUTTER_SPEED = "IMG_RES_SHUTTER_SPEED"
private const val IMG_RES_FOCAL_LENGTH = "IMG_RES_FOCAL_LENGTH"
private const val IMG_RES_METERING_MODE = "IMG_RES_METERING_MODE"
private const val IMG_RES_FLASH = "IMG_RES_FLASH"
private const val IMG_RES_STRIP_OFFSETS = "IMG_RES_STRIP_OFFSETS"
private const val IMG_RES_GPS_VERSION_ID = "IMG_RES_GPS_VERSION_ID"
private const val IMG_RES_GPS_LATITUDE = "IMG_RES_GPS_LATITUDE"
private const val IMG_RES_GPS_LONGITUDE = "IMG_RES_GPS_LONGITUDE"
private const val IMG_RES_GPS_ALTITUDE = "IMG_RES_GPS_ALTITUDE"
private const val IMG_RES_DATE_TIME_ORIGINAL = "IMG_RES_DATE_TIME_ORIGINAL"
private const val IMG_RES_CHANGE_AND_DATE_TIME = "IMG_RES_CHANGE_AND_DATE_TIME"

// 横スライド時に必要なデータを格納する変数
var imageResId: Int? = null
var imageResPath: String? = null
var imageResName: String? = null
var imageResSentence1: String? = null
var imageResSentence2: String? = null
var imageResSentence3: String? = null
var imageResPhotoId: Int? = null
var imageResImageLength: String? = null
var imageResImageWidth: String? = null
var imageResYResolution: String? = null
var imageResXResolution: String? = null
var imageResBitsPerSample: String? = null
var imageResCompression: String? = null
var imageResImageOrientation: String? = null
var imageResImageDescription: String? = null
var imageResArtist: String? = null
var imageResMaker: String? = null
var imageResModel: String? = null
var imageResAperture: String? = null
var imageResExposureTime: String? = null
var imageResIsoSpeed: String? = null
var imageResExposureBias: String? = null
var imageResFNumber: String? = null
var imageResShutterSpeed: String? = null
var imageResFocalLength: String? = null
var imageResMeteringMode: String? = null
var imageResFlash: String? = null
var imageResStripOffsets: String? = null
var imageResGpsVersionID: String? = null
var imageResGpsLatitude: String? = null
var imageResGpsLongitude: String? = null
var imageResGpsAltitude: String? = null
var imageResDateTimeOriginal: String? = null
var imageResChangeDateAndTime: String? = null


class PhotoDetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            imageResId = it.getInt(IMG_RES_ID)
            imageResPath = it.getString(IMG_RES_PATH)
            imageResName = it.getString(IMG_RES_NAME)
            imageResSentence1 = it.getString(IMG_RES_SENTENCE1)
            imageResSentence2 = it.getString(IMG_RES_SENTENCE2)
            imageResSentence3 = it.getString(IMG_RES_SENTENCE3)
            imageResPhotoId = it.getInt(IMG_RES_PHOTO_ID)
            imageResImageLength = it.getString(IMG_RES_IMAGE_LENGTH)
            imageResImageWidth = it.getString(IMG_RES_IMAGE_WIDTH)
            imageResYResolution = it.getString(IMG_RES_Y_RESOLUTION)
            imageResXResolution = it.getString(IMG_RES_X_RESOLUTION)
            imageResBitsPerSample = it.getString(IMG_RES_BITS_PER_SAMPLE)
            imageResCompression = it.getString(IMG_RES_COMPRESSION)
            imageResImageOrientation = it.getString(IMG_RES_IMAGE_ORIENTATION)
            imageResImageDescription = it.getString(IMG_RES_IMAGE_DESCRIPTION)
            imageResArtist = it.getString(IMG_RES_ARTIST)
            imageResMaker = it.getString(IMG_RES_MAKER)
            imageResModel = it.getString(IMG_RES_MODEL)
            imageResAperture = it.getString(IMG_RES_APERTURE)
            imageResExposureTime = it.getString(IMG_RES_EXPOSURE_TIME)
            imageResIsoSpeed = it.getString(IMG_RES_ISO_SPEED)
            imageResExposureBias = it.getString(IMG_RES_EXPOSURE_BIAS)
            imageResFNumber = it.getString(IMG_RES_F_NUMBER)
            imageResShutterSpeed = it.getString(IMG_RES_SHUTTER_SPEED)
            imageResFocalLength = it.getString(IMG_RES_FOCAL_LENGTH)
            imageResMeteringMode = it.getString(IMG_RES_METERING_MODE)
            imageResFlash = it.getString(IMG_RES_FLASH)
            imageResStripOffsets = it.getString(IMG_RES_STRIP_OFFSETS)
            imageResGpsVersionID = it.getString(IMG_RES_GPS_VERSION_ID)
            imageResGpsLatitude = it.getString(IMG_RES_GPS_LATITUDE)
            imageResGpsLongitude = it.getString(IMG_RES_GPS_LONGITUDE)
            imageResGpsAltitude = it.getString(IMG_RES_GPS_ALTITUDE)
            imageResDateTimeOriginal = it.getString(IMG_RES_DATE_TIME_ORIGINAL)
            imageResChangeDateAndTime = it.getString(IMG_RES_CHANGE_AND_DATE_TIME)

        }
    }

    private var _binding: FragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)


        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance(
            imageResId: Int?,
            imageResPath: String?,
            imageResName: String?,
            imageResSentence1: String?,
            imageResSentence2: String?,
            imageResSentence3: String?,
            imageResPhotoId: Int?,
            imageResImageLength: String?,
            imageResImageWidth: String?,
            imageResYResolution: String?,
            imageResXResolution: String?,
            imageResBitsPerSample: String?,
            imageResCompression: String?,
            imageResImageOrientation: String?,
            imageResImageDescription: String?,
            imageResArtist: String?,
            imageResMaker: String?,
            imageResModel: String?,
            imageResAperture: String?,
            imageResExposureTime: String?,
            imageResIsoSpeed: String?,
            imageResExposureBias: String?,
            imageResFNumber: String?,
            imageResShutterSpeed: String?,
            imageResFocalLength: String?,
            imageResMeteringMode: String?,
            imageResFlash: String?,
            imageResStripOffsets: String?,
            imageResGpsVersionID: String?,
            imageResGpsLatitude: String?,
            imageResGpsLongitude: String?,
            imageResGpsAltitude: String?,
            imageResDateTimeOriginal: String?,
            imageResChangeDateAndTime: String?,


            ) =
            PhotoDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(IMG_RES_ID, imageResId!!)
                    putString(IMG_RES_PATH, imageResPath)
                    putString(IMG_RES_NAME, imageResName)
                    putString(IMG_RES_SENTENCE1, imageResSentence1)
                    putString(IMG_RES_SENTENCE2, imageResSentence2)
                    putString(IMG_RES_SENTENCE3, imageResSentence3)
                    putInt(IMG_RES_PHOTO_ID, imageResPhotoId!!)
                    putString(IMG_RES_IMAGE_LENGTH, imageResImageLength)
                    putString(IMG_RES_IMAGE_WIDTH, imageResImageWidth)
                    putString(IMG_RES_Y_RESOLUTION, imageResYResolution)
                    putString(IMG_RES_X_RESOLUTION, imageResXResolution)
                    putString(IMG_RES_BITS_PER_SAMPLE, imageResBitsPerSample)
                    putString(IMG_RES_COMPRESSION, imageResCompression)
                    putString(IMG_RES_IMAGE_ORIENTATION, imageResImageOrientation)
                    putString(IMG_RES_IMAGE_DESCRIPTION, imageResImageDescription)
                    putString(IMG_RES_ARTIST, imageResArtist)
                    putString(IMG_RES_MAKER, imageResMaker)
                    putString(IMG_RES_MODEL, imageResModel)
                    putString(IMG_RES_APERTURE, imageResAperture)
                    putString(IMG_RES_EXPOSURE_TIME, imageResExposureTime)
                    putString(IMG_RES_ISO_SPEED, imageResIsoSpeed)
                    putString(IMG_RES_EXPOSURE_BIAS, imageResExposureBias)
                    putString(IMG_RES_F_NUMBER, imageResFNumber)
                    putString(IMG_RES_SHUTTER_SPEED, imageResShutterSpeed)
                    putString(IMG_RES_FOCAL_LENGTH, imageResFocalLength)
                    putString(IMG_RES_METERING_MODE, imageResMeteringMode)
                    putString(IMG_RES_FLASH, imageResFlash)
                    putString(IMG_RES_STRIP_OFFSETS, imageResStripOffsets)
                    putString(IMG_RES_GPS_VERSION_ID, imageResGpsVersionID)
                    putString(IMG_RES_GPS_LATITUDE, imageResGpsLatitude)
                    putString(IMG_RES_GPS_LONGITUDE, imageResGpsLongitude)
                    putString(IMG_RES_GPS_ALTITUDE, imageResGpsAltitude)
                    putString(IMG_RES_DATE_TIME_ORIGINAL, imageResDateTimeOriginal)
                    putString(IMG_RES_CHANGE_AND_DATE_TIME, imageResChangeDateAndTime)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 横スライド時の画像をセット
        val f: File = File(imageResPath)
        val uri = Uri.fromFile(f)
        uri?.let {
            binding.imageView.setImageURI(it)
        }

    }


    // アクティビティが完全に表示されたときに表示
    override fun onResume() {
        super.onResume()

        // アプリバーの表示
        // タイトル
        (activity as AppCompatActivity).supportActionBar?.title = imageResName

        // キャプションフラグメントをデフォルト表示にする
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#dddddd")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#ffffff")))

            replace(R.id.container, CaptionFragment())
            commit()
        }

        // キャプションボタンをクリックしたときのフラグメント動作
        binding.captionButton.setOnClickListener {
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#dddddd")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#ffffff")))

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, CaptionFragment())
                commit()
            }
        }

        // Exifボタンをクリックしたときのフラグメント動作
        binding.exifButton.setOnClickListener {
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#dddddd")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#ffffff")))

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, ExifFragment())
                commit()
            }
        }

        // OCRボタンをクリックしたときのフラグメント動作
        binding.ocrButton.setOnClickListener {
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#dddddd")))

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, OcrFragment())
                commit()
            }
        }

        Log.d("imageResId", imageResId.toString())
        Log.d("imageResPhotoId", imageResPhotoId.toString())
        Log.d("imageResPath", imageResPath.toString())
        Log.d("imageResName", imageResName.toString())
        Log.d("imageResSentence1", imageResSentence1.toString())
        Log.d("imageResSentence2", imageResSentence2.toString())
        Log.d("imageResSentence3", imageResSentence3.toString())

    }

    override fun onPause() {
        super.onPause()
        // スライド時、前の状態が残っていて不具合が起きたので削除するようにする
        getFragmentManager()?.beginTransaction()?.remove(this)?.commit()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}