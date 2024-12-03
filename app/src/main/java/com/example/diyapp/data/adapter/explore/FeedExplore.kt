package com.example.diyapp.data.adapter.explore

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedExplore(
    @SerializedName("id_publicacion") val idPublication: Int,
    @SerializedName("id_usuarios") val user: String,
    @SerializedName("titulo") val title: String,
    @SerializedName("nombre_tema") val theme: String,
    @SerializedName("foto_portada") val photoMain: String,
    @SerializedName("descripcion") val description: String,
    @SerializedName("num_likes") val numLikes: Int,
    @SerializedName("estado") val state: Int,
    @SerializedName("fecha_creacion") val dateCreation: String,
    @SerializedName("instrucciones") val instructions: String,
    @SerializedName("foto_proceso") val photoProcess: List<String>
) : Parcelable