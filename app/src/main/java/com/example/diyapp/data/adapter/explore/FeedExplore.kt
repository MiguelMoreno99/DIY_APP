package com.example.diyapp.data.adapter.explore

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class feedExplore(
    @SerializedName("idPublication") val idPublication: Int, //EL SERIALIZED NAME DEBE SER EXACTAMENTE IGUAL AL DE LA API
    @SerializedName("User") val User: String,
    @SerializedName("title") val title: String,
    @SerializedName("Theme") val Theme: String,
    @SerializedName("photoMain") val photoMain: ByteArray?,
    @SerializedName("photoProcess") val photoProcess: List<ByteArray>?,
    @SerializedName("instructions") val instructions: String,
    @SerializedName("description") val description: String,
    @SerializedName("numLikes") val numLikes: Int,
    @SerializedName("state") val state: Boolean,
    @SerializedName("dateCreation") val dateCreation: String
) : Parcelable {
}