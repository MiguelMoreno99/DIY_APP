package com.example.diyapp.data.adapter.favorites

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R

class feedFavoritesAdapter(
    private val feedFavoritesList: List<feedFavorites>,
    private val onClick: (feedFavorites) -> Unit
) :
    RecyclerView.Adapter<feedFavoritesAdapter.feedFavoritesViewHolder>() {

    private var filteredList = feedFavoritesList.toMutableList()

    inner class feedFavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val UserName: TextView = view.findViewById(R.id.tvUserName)
        val Category: TextView = view.findViewById(R.id.tvCategory)
        val Title: TextView = view.findViewById(R.id.tvTitle)
        val LikesCountNumber: TextView = view.findViewById(R.id.tvLikesCountNumber)
        val CreationDate: TextView = view.findViewById(R.id.tvCreationDate)
        val photoMain: ImageView = view.findViewById(R.id.ivMainImage)
        fun render(feedFavoritesModel: feedFavorites) {
            UserName.text = feedFavoritesModel.User
            Category.text = feedFavoritesModel.Theme
            Title.text = feedFavoritesModel.title
            LikesCountNumber.text = feedFavoritesModel.numLikes.toString()
            CreationDate.text = feedFavoritesModel.dateCreation

            val photoMainBytes = Base64.decode(feedFavoritesModel.photoMain, Base64.DEFAULT)
            val photoMainBitmap =
                BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
            photoMain.setImageBitmap(photoMainBitmap)
            itemView.setOnClickListener { onClick(feedFavoritesModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedFavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return feedFavoritesViewHolder(
            layoutInflater.inflate(
                R.layout.item_feedfavorites,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: feedFavoritesViewHolder, position: Int) {
        val item = filteredList[position]
        holder.render(item)
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            feedFavoritesList.toMutableList()
        } else {
            feedFavoritesList.filter {
                it.Theme.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}