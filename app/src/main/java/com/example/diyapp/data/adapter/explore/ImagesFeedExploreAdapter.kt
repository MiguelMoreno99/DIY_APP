package com.example.diyapp.data.adapter.explore

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R

class ImagesFeedExploreAdapter(private val imagenes: List<String>) :
    RecyclerView.Adapter<ImagesFeedExploreAdapter.ViewHolder>() {

    // ViewHolder interno para manejar cada imagen individual
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagenItemImageView: ImageView =
            itemView.findViewById(R.id.recyclerViewInstructionPhotos)

        // Método para enlazar la imagen Base64 al ImageView
        fun bind(imagenBlob: String) {
            val decodedBytes = Base64.decode(imagenBlob, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            imagenItemImageView.setImageBitmap(bitmap)
        }
    }

    // Inflar el layout para cada imagen
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_imageinstructions,
                parent,
                false
            )
        )
    }

    // Llamar a bind() para asignar datos a cada vista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imagenes[position])
    }

    // Cantidad de elementos en la lista
    override fun getItemCount(): Int = imagenes.size
}