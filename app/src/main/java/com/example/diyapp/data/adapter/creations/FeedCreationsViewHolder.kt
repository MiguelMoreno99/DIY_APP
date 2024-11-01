package com.example.diyapp.data.adapter.creations

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.diyapp.R

class feedCreationsViewHolder(view: View):ViewHolder(view) {

    val UserName = view.findViewById<TextView>(R.id.tvUserName)
    val Category = view.findViewById<TextView>(R.id.tvCategory)
    val Title = view.findViewById<TextView>(R.id.tvTitle)
    val LikesCountNumber = view.findViewById<TextView>(R.id.tvLikesCountNumber)
    val CreationDate = view.findViewById<TextView>(R.id.tvCreationDate)

    fun render(feedCreationsModel: feedCreations){
        UserName.text = feedCreationsModel.User
        Category.text = feedCreationsModel.Theme
        Title.text = feedCreationsModel.title
        LikesCountNumber.text = feedCreationsModel.numLikes.toString()
        CreationDate.text = feedCreationsModel.dateCreation
    }
}