package com.example.diyapp.data.adapter.explore

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R

class feedExploreAdapter(
    private var feedExplorerList: List<feedExplore>,
    private val onClick: (feedExplore) -> Unit
) :
    RecyclerView.Adapter<feedExploreAdapter.feedExploreViewHolder>() {

    private var filteredList = feedExplorerList.toMutableList()

    inner class feedExploreViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val UserName: TextView = view.findViewById(R.id.tvUserName)
        val Category: TextView = view.findViewById(R.id.tvCategory)
        val Title: TextView = view.findViewById(R.id.tvTitle)
        val LikesCountNumber: TextView = view.findViewById(R.id.tvLikesCountNumber)
        val CreationDate: TextView = view.findViewById(R.id.tvCreationDate)
        val photoMain: ImageView = view.findViewById(R.id.ivMainImage)
        fun render(feedExploreModel: feedExplore) {
            UserName.text = feedExploreModel.User
            Category.text = feedExploreModel.Theme
            Title.text = feedExploreModel.title
            LikesCountNumber.text = feedExploreModel.numLikes.toString()
            CreationDate.text = feedExploreModel.dateCreation

            val photoMainBytes = Base64.decode(feedExploreModel.photoMain, Base64.DEFAULT)
            val photoMainBitmap =
                BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
            photoMain.setImageBitmap(photoMainBitmap)
            itemView.setOnClickListener { onClick(feedExploreModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedExploreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return feedExploreViewHolder(
            layoutInflater.inflate(
                R.layout.item_feedexplore,
                parent,
                false
            )
        )
    }

    fun updateData(newData: List<feedExplore>) {
        filteredList.clear()
        filteredList.addAll(newData)
        feedExplorerList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: feedExploreViewHolder, position: Int) {
        val item = filteredList[position]
        holder.render(item)
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            feedExplorerList.toMutableList()
        } else {
            feedExplorerList.filter {
                it.Theme.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}