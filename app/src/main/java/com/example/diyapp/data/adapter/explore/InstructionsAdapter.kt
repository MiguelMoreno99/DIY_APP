package com.example.diyapp.data.adapter.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils

class InstructionsAdapter(private val photoProcess: List<String>) :
    RecyclerView.Adapter<InstructionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val instructionImageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(imageBlob: String) {
            instructionImageView.setImageBitmap(ImageUtils.base64ToBitmap(imageBlob))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imageinstructions, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photoProcess[position])
    }

    override fun getItemCount(): Int = photoProcess.size
}
