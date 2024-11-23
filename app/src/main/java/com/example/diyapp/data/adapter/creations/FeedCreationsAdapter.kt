package com.example.diyapp.data.adapter.creations

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R

class feedCreationsAdapter(
    private var feedCreationsList: List<feedCreations>,
    private val onClick: (feedCreations) -> Unit
) :
    RecyclerView.Adapter<feedCreationsAdapter.feedCreationsViewHolder>() {

    private var filteredList = feedCreationsList.toMutableList()

    inner class feedCreationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val UserName: TextView = view.findViewById(R.id.tvUserName)
        val Category: TextView = view.findViewById(R.id.tvCategory)
        val Title: TextView = view.findViewById(R.id.tvTitle)
        val LikesCountNumber: TextView = view.findViewById(R.id.tvLikesCountNumber)
        val CreationDate: TextView = view.findViewById(R.id.tvCreationDate)
        val photoMain: ImageView = view.findViewById(R.id.ivMainImage)
        fun render(feedCreationsModel: feedCreations) {
            UserName.text = feedCreationsModel.User
            Category.text = feedCreationsModel.Theme
            Title.text = feedCreationsModel.title
            LikesCountNumber.text = feedCreationsModel.numLikes.toString()
            CreationDate.text = feedCreationsModel.dateCreation

            val photoMainBytes = Base64.decode(feedCreationsModel.photoMain, Base64.DEFAULT)
            val photoMainBitmap =
                BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
            photoMain.setImageBitmap(photoMainBitmap)
            itemView.setOnClickListener { onClick(feedCreationsModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedCreationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return feedCreationsViewHolder(
            layoutInflater.inflate(
                R.layout.item_feedcreations,
                parent,
                false
            )
        )
    }

    fun updateData(newData: List<feedCreations>) {
        filteredList.clear()
        filteredList.addAll(newData)
        feedCreationsList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: feedCreationsViewHolder, position: Int) {
        val item = filteredList[position]
        holder.render(item)
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            feedCreationsList.toMutableList()
        } else {
            feedCreationsList.filter {
                it.Theme.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}