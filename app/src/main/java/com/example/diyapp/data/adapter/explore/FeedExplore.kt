package com.example.diyapp.data.adapter.explore

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class feedExplore(
    val idPublication: Int,
    val User: String,
    val title: String,
    val Theme: String,
    val photoMain: ByteArray?,
    val photoProcess: List<ByteArray>?,
    val instructions: String,
    val description: String,
    val numLikes: Int,
    val state: Boolean,
    val dateCreation: String
) : Parcelable {
}