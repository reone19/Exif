package com.example.exif.model

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.exif.R
import com.example.exif.YoshidanoYatu


class PhotoAdapterAlbum(private var context: Context, private var imagesList: ArrayList<Image>) :
    RecyclerView.Adapter<PhotoAdapterAlbum.ImageViewHolder>(){
    var a:Int = 0
    val dbimageId = IntArray(999)

    class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var image: ImageView? = null
        var image1: ImageView? = null

        init{
            image= itemView.findViewById(R.id.row_image)
            image1= itemView.findViewById(R.id.row_image1)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_custom_recycler_item,parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentImage=imagesList[position]
        Glide.with(context)
            .load(currentImage.imagePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.image!!)
        //MediaStoreのデータベースから取得した画像をタップしたらインテントで画面遷移
        holder.image?.setOnClickListener {
            holder.image1?.setBackgroundColor(Color.argb(100,0,0,0))
            dbimageId[a] = currentImage.imageid?.toInt()!!
            a = a + 1
        }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}