package com.example.diyapp.data.adapter.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R

class feedExploreAdapter(private val feedExplorerList: List<feedExplore>) :
    RecyclerView.Adapter<feedExploreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedExploreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return feedExploreViewHolder(layoutInflater.inflate(R.layout.item_feedexplore,parent,false))
    }

    override fun getItemCount(): Int {
        return feedExplorerList.size
    }

    override fun onBindViewHolder(holder: feedExploreViewHolder, position: Int) {
        val item = feedExplorerList[position]
        holder.render(item)
    }
}