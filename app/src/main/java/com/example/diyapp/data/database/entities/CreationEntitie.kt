package com.example.diyapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.diyapp.data.database.Converters
import com.example.diyapp.data.model.CreationModel

@Entity(
    tableName = "CreationTable"
)
@TypeConverters(Converters::class)
data class CreationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idPublication") val idPublication: Int = 0,
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "theme") val theme: String = "",
    @ColumnInfo(name = "photoMain") val photoMain: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "numLikes") val numLikes: Int = 0,
    @ColumnInfo(name = "state") val state: Int = 0,
    @ColumnInfo(name = "dateCreation") val dateCreation: String = "",
    @ColumnInfo(name = "instructions") val instructions: String = "",
    @ColumnInfo(name = "photoProcess") val photoProcess: List<String>
)

fun CreationModel.toDatabase() = CreationEntity(
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