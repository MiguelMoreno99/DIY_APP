package com.example.diyapp.data.adapter.creations

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class feedCreations(
    val idPublication: Int,
    val User: String,
    val title: String,
    val Theme: String,
    val photoMain: String,
    val photoProcess: String,
    val instructions: String,
    val description: String,
    val numLikes: Int,
    val state: Boolean,
    val dateCreation: String
) : Parcelable {
}