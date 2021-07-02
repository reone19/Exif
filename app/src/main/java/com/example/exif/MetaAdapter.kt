package com.example.exif

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exif.model.Meta
import com.example.exif.model.Photo
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class MetaAdapter(data: OrderedRealmCollection<Meta>) :
    RealmRecyclerViewAdapter<Meta, MetaAdapter.ViewHolder>(data, true) {

    init {
        setHasStableIds(true)
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val deleteDate: TextView = cell.findViewById(android.R.id.text1)
        val photoName: TextView = cell.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): MetaAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MetaAdapter.ViewHolder, position: Int ) {
        val photo: Photo? = getItem(position)
        holder.deleteDate = photo?.deleteDate
        holder.photoName.text = photo?.name
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }

}