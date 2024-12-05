package com.example.diyapp.data.adapter.explore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils

class FeedExploreAdapter(
    private var feedExplorerList: List<FeedExplore>,
    private val onClick: (FeedExplore) -> Unit
) :
    RecyclerView.Adapter<FeedExploreAdapter.FeedExploreViewHolder>() {

    private var filteredList = feedExplorerList.toMutableList()

    inner class FeedExploreViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val userName: TextView = view.findViewById(R.id.tvUserName)
        private val category: TextView = view.findViewById(R.id.tvCategory)
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val likesCountNumber: TextView = view.findViewById(R.id.tvLikesCountNumber)
        private val creationDate: TextView = view.findViewById(R.id.tvCreationDate)
        private val photoMain: ImageView = view.findViewById(R.id.ivMainImage)
        fun render(feedExploreModel: FeedExplore) {
            userName.text = feedExploreModel.email
            category.text = feedExploreModel.theme
            title.text = feedExploreModel.title
            likesCountNumber.text = feedExploreModel.numLikes.toString()
            creationDate.text = feedExploreModel.dateCreation

            photoMain.setImageBitmap(ImageUtils.base64ToBitmap(feedExploreModel.photoMain))

            itemView.setOnClickListener { onClick(feedExploreModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedExploreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FeedExploreViewHolder(
            layoutInflater.inflate(
                R.layout.item_feedexplore,
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<FeedExplore>) {
        filteredList.clear()
        filteredList.addAll(newData)
        feedExplorerList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: FeedExploreViewHolder, position: Int) {
        val item = filteredList[position]
        holder.render(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            feedExplorerList.toMutableList()
        } else {
            feedExplorerList.filter {
                it.theme.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}