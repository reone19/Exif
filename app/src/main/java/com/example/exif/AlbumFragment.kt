package com.example.exif

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class AlbumFragment : Fragment() {

    companion object {
        private const val TAG = "AlbumFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backButton = view.findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener{
            Log.d(TAG, "BackButton pressed!")
            fragmentManager?.popBackStack()
        }

        //「追加」を押したときにレイアウトを追加
        val addButton = view.findViewById<Button>(R.id.add_album)
        addButton.setOnClickListener{

            //「MainLinearLayout」というidに「album_sub.xml」を追加
            val Main = view.findViewById<LinearLayout>(R.id.MainLinearLayout)
            layoutInflater.inflate(R.layout.album_sub,Main)
        }
    }
}