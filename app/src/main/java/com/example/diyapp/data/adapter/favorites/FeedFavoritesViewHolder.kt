package com.example.diyapp.data.adapter.favorites

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.diyapp.R
import com.example.diyapp.data.adapter.favorites.feedFavorites

class feedFavoritesViewHolder(view: View):ViewHolder(view) {

    val UserName = view.findViewById<TextView>(R.id.tvUserName)
    val Category = view.findViewById<TextView>(R.id.tvCategory)
    val Title = view.findViewById<TextView>(R.id.tvTitle)
    val LikesCountNumber = view.findViewById<TextView>(R.id.tvLikesCountNumber)
    val CreationDate = view.findViewById<TextView>(R.id.tvCreationDate)

    fun render(feedFavoritesModel: feedFavorites){
        UserName.text = feedFavoritesModel.User
        Category.text = feedFavoritesModel.Theme
        Title.text = feedFavoritesModel.title
        LikesCountNumber.text = feedFavoritesModel.numLikes.toString()
        CreationDate.text = feedFavoritesModel.dateCreation
    }
}