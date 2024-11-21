package com.example.diyapp.data.adapter.create

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R

class MultipleImagesAdapterAdapter(
    private val imageUris: MutableList<Uri>,
) :
    RecyclerView.Adapter<MultipleImagesAdapterAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_imageinstructions, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageUris.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = imageUris[position]
        holder.imageView.setImageURI(item)
    }
}