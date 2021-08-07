package com.example.exif

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.exif.databinding.FragmentPhotoDetailBinding
import java.io.File

private const val IMG_RES_PATH = "IMG_RES_PATH"
private const val IMG_RES_NAME = "IMG_RES_NAME"

class PhotoDetailFragment : Fragment() {
    private var imageResPath: String? = null
    private var imageResName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageResPath = it.getString(IMG_RES_PATH)
            imageResName = it.getString(IMG_RES_NAME)
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
        fun newInstance(imageResPath: String, imageResName:String) =
            PhotoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(IMG_RES_PATH, imageResPath)
                    putString(IMG_RES_NAME, imageResName)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // アプリバーの表示
        // タイトル
        (activity as AppCompatActivity).supportActionBar?.title = imageResName

        val f: File = File(imageResPath)
        val uri = Uri.fromFile(f)
        uri?.let {
            binding.imageView.setImageURI(it)
        }
//        Log.e("a", uri.toString())

        // キャプションフラグメントをデフォルト表示にする
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.container, CaptionFragment())
            addToBackStack(null)
            commit()
        }

        // キャプションボタンをクリックしたときのフラグメント動作
        binding.captionButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, CaptionFragment())
                addToBackStack(null)
                commit()
            }
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#dddddd")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#ffffff")))
        }

        // Exifボタンをクリックしたときのフラグメント動作
        binding.exifButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, ExifFragment())
                addToBackStack(null)
                commit()
            }
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#dddddd")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#ffffff")))
        }

        // OCRボタンをクリックしたときのフラグメント動作
        binding.ocrButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, OcrFragment())
                addToBackStack(null)
                commit()
            }
            // ボタン背景色変更
            binding.captionButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.exifButton.setBackgroundColor((Color.parseColor("#ffffff")))
            binding.ocrButton.setBackgroundColor((Color.parseColor("#dddddd")))
        }
    }

}