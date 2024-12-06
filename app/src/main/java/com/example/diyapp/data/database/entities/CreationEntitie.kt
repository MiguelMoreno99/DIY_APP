package com.example.diyapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "CreationTable", foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["email"],
            childColumns = ["email"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CreationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idPublication") val idPublication: Int = 0,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "theme") val theme: String?,
    @ColumnInfo(name = "photoMain") val photoMain: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "numLikes") val numLikes: Int = 0,
    @ColumnInfo(name = "state") val state: Int = 0,
    @ColumnInfo(name = "dateCreation") val dateCreation: String?,
    @ColumnInfo(name = "instructions") val instructions: String?,
    @ColumnInfo(name = "photoProcess") val photoProcess: List<String>?
)