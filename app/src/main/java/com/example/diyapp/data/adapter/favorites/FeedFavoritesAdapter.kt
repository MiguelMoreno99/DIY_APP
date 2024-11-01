package com.example.diyapp.data.adapter.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.favorites.feedFavorites
import com.example.diyapp.data.adapter.favorites.feedFavoritesViewHolder

class feedFavoritesAdapter(private val feedFavoritesList: List<feedFavorites>) :
    RecyclerView.Adapter<feedFavoritesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedFavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return feedFavoritesViewHolder(layoutInflater.inflate(R.layout.item_feedfavorites,parent,false))
    }

    override fun getItemCount(): Int {
        return feedFavoritesList.size
    }

    override fun onBindViewHolder(holder: feedFavoritesViewHolder, position: Int) {
        val item = feedFavoritesList[position]
        holder.render(item)
    }
}