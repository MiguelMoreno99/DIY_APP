package com.example.diyapp.data.adapter.explore

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.diyapp.R

class feedExploreViewHolder(view: View):ViewHolder(view) {

    val UserName = view.findViewById<TextView>(R.id.tvUserName)
    val Category = view.findViewById<TextView>(R.id.tvCategory)
    val Title = view.findViewById<TextView>(R.id.tvTitle)
    val LikesCountNumber = view.findViewById<TextView>(R.id.tvLikesCountNumber)
    val CreationDate = view.findViewById<TextView>(R.id.tvCreationDate)

    fun render(feedExploreModel: feedExplore){
        UserName.text = feedExploreModel.User
        Category.text = feedExploreModel.Theme
        Title.text = feedExploreModel.title
        LikesCountNumber.text = feedExploreModel.numLikes.toString()
        CreationDate.text = feedExploreModel.dateCreation
    }
}