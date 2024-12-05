package com.example.diyapp.data.adapter.creations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils

class FeedCreationsAdapter(
    private var feedCreationsList: List<FeedCreations>,
    private val onClick: (FeedCreations) -> Unit
) :
    RecyclerView.Adapter<FeedCreationsAdapter.FeedCreationsViewHolder>() {

    private var filteredList = feedCreationsList.toMutableList()

    inner class FeedCreationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val userName: TextView = view.findViewById(R.id.tvUserName)
        private val category: TextView = view.findViewById(R.id.tvCategory)
        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val likesCountNumber: TextView = view.findViewById(R.id.tvLikesCountNumber)
        private val creationDate: TextView = view.findViewById(R.id.tvCreationDate)
        private val photoMain: ImageView = view.findViewById(R.id.ivMainImage)
        fun render(feedCreationsModel: FeedCreations) {
            userName.text = feedCreationsModel.email
            category.text = feedCreationsModel.theme
            title.text = feedCreationsModel.title
            likesCountNumber.text = feedCreationsModel.numLikes.toString()
            creationDate.text = feedCreationsModel.dateCreation

            photoMain.setImageBitmap(ImageUtils.base64ToBitmap(feedCreationsModel.photoMain))

            itemView.setOnClickListener { onClick(feedCreationsModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedCreationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FeedCreationsViewHolder(
            layoutInflater.inflate(
                R.layout.item_feedcreations,
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<FeedCreations>) {
        filteredList.clear()
        filteredList.addAll(newData)
        feedCreationsList = filteredList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteData() {
        filteredList.clear()
        feedCreationsList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: FeedCreationsViewHolder, position: Int) {
        val item = filteredList[position]
        holder.render(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            feedCreationsList.toMutableList()
        } else {
            feedCreationsList.filter {
                it.theme.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}