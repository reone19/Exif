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
import kotlinx.android.synthetic.main.fragment_caption.view.*
import java.io.File

// ID
private const val IMG_RES_PATH = "IMG_RES_PATH"
private const val IMG_RES_NAME = "IMG_RES_NAME"
private const val IMG_RES_SENTENCE1 = "IMG_RES_SENTENCE1"
private const val IMG_RES_SENTENCE2 = "IMG_RES_SENTENCE2"
private const val IMG_RES_SENTENCE3 = "IMG_RES_SENTENCE3"

class PhotoDetailFragment : Fragment() {
    // 横スライド時に必要なデータを格納する変数
    private var imageResPath: String? = null
    private var imageResName: String? = null
    private var imageResSentence1: String? = null
    private var imageResSentence2: String? = null
    private var imageResSentence3: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageResPath = it.getString(IMG_RES_PATH)
            imageResName = it.getString(IMG_RES_NAME)
            imageResSentence1 = it.getString(IMG_RES_SENTENCE1)
            imageResSentence2 = it.getString(IMG_RES_SENTENCE2)
            imageResSentence3 = it.getString(IMG_RES_SENTENCE3)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(
            imageResPath: String?,
            imageResName: String?,
            imageResSentence1: String?,
            imageResSentence2: String?,
            imageResSentence3: String?
        ) =
            PhotoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(IMG_RES_PATH, imageResPath)
                    putString(IMG_RES_NAME, imageResName)
                    putString(IMG_RES_SENTENCE1, imageResSentence1)
                    putString(IMG_RES_SENTENCE2, imageResSentence2)
                    putString(IMG_RES_SENTENCE3, imageResSentence3)
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


        // キャプションのEditTextにデータをセットする（できない）
        try {
            binding.container.capString1.setText(imageResSentence1)
            binding.container.capString2.setText(imageResSentence2)
            binding.container.capString3.setText(imageResSentence3)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        // キャプションフラグメントをデフォルト表示にする
//        activity?.supportFragmentManager?.beginTransaction()?.apply {
//            replace(R.id.container, CaptionFragment())
//            addToBackStack(null)
//            commit()
//        }

        // キャプションボタンをクリックしたときのフラグメント動作
        binding.captionButton.setOnClickListener {
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#dddddd")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#ffffff")))

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, CaptionFragment())
                addToBackStack(null)
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
                addToBackStack(null)
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
                addToBackStack(null)
                commit()
            }
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
            addToBackStack(null)
            commit()
        }

        // _binding?.container?.capString2?.setText(imageResSentence2)
        // _binding?.container?.capString3?.setText(imageResSentence3)
        Log.e("imageResPath", imageResPath.toString())
        Log.e("imageResName", imageResName.toString())
        Log.e("imageResSentence1", imageResSentence1.toString())
        Log.e("imageResSentence2", imageResSentence2.toString())
        Log.e("imageResSentence3", imageResSentence3.toString())

        Log.e("capString1", binding.container.capString1?.text.toString())
    }

}