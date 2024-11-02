package com.example.diyapp.data.adapter.creations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R

class feedCreationsAdapter(
    private val feedCreationsList: List<feedCreations>,
    private val onClick: (feedCreations) -> Unit
) :
    RecyclerView.Adapter<feedCreationsViewHolder>() {
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

    override fun getItemCount(): Int {
        return feedCreationsList.size
    }

    override fun onBindViewHolder(holder: feedCreationsViewHolder, position: Int) {
        val item = feedCreationsList[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }
}