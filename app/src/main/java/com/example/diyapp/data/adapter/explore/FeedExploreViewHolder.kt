package com.example.diyapp.data.adapter.explore

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.diyapp.R

class feedExploreViewHolder(view: View): ViewHolder(view) {

    val UserName = view.findViewById<TextView>(R.id.tvUserName)
    val Category = view.findViewById<TextView>(R.id.tvCategory)
    val Title = view.findViewById<TextView>(R.id.tvTitle)
    val LikesCountNumber = view.findViewById<TextView>(R.id.tvLikesCountNumber)
    val CreationDate = view.findViewById<TextView>(R.id.tvCreationDate)
    val photoMain = view.findViewById<ImageView>(R.id.ivMainImage)
    //val photoProcess = view.findViewById<RecyclerView>(R.id.recyclerViewInstructionPhotos)


    fun render(feedExploreModel: feedExplore){
        UserName.text = feedExploreModel.User
        Category.text = feedExploreModel.Theme
        Title.text = feedExploreModel.title
        LikesCountNumber.text = feedExploreModel.numLikes.toString()
        CreationDate.text = feedExploreModel.dateCreation

        // Decodificar y mostrar la portada
        val photoMainBytes = Base64.decode(feedExploreModel.photoMain, Base64.DEFAULT)
        val photoMainBitmap = BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
        photoMain.setImageBitmap(photoMainBitmap)

        // Configurar RecyclerView para imágenes adicionales
        //photoProcess.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        //photoProcess.adapter = ImagesFeedExploreAdapter(feedExploreModel.photoProcess)
    }
}