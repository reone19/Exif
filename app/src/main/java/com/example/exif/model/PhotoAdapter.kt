package com.example.exif.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.exif.R
import com.example.exif.YoshidanoYatu


class PhotoAdapter(private var context: Context, private var imagesList: ArrayList<Image>) :
    RecyclerView.Adapter<PhotoAdapter.ImageViewHolder>(){

    class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var image: ImageView? = null

        init{
            image= itemView.findViewById(R.id.row_image)
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
            val intent = Intent(context, YoshidanoYatu::class.java)
            intent.putExtra("id", currentImage.imageid)
            intent.putExtra("path", currentImage.imagePath)
            intent.putExtra("name", currentImage.imageName)
            intent.putExtra("sentence1", currentImage.imageSentence1)
            intent.putExtra("sentence2", currentImage.imageSentence2)
            intent.putExtra("sentence3", currentImage.imageSentence3)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}