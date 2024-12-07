package com.example.diyapp.data.model

import android.os.Parcelable
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.data.database.entities.CreationEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreationModel(
    val idPublication: Int,
    val email: String,
    val title: String,
    val theme: String,
    val photoMain: String,
    val description: String,
    val numLikes: Int,
    val state: Int,
    val dateCreation: String,
    val instructions: String,
    val photoProcess: List<String>
) : Parcelable

fun FeedExplore.toDomain() = CreationModel(
    idPublication,
    email,
    title,
    theme,
    photoMain,
    description,
    numLikes,
    state,
    dateCreation,
    instructions,
    photoProcess
)

fun CreationEntity.toDomain() = CreationModel(
    idPublication,
    email,
    title,
    theme,
    photoMain,
    description,
    numLikes,
    state,
    dateCreation,
    instructions,
    photoProcess.toMutableList()
)