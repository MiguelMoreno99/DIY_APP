package com.example.diyapp.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "favoritesTable",
    primaryKeys = ["email", "idPublication"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["email"],
            childColumns = ["email"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CreationEntity::class,
            parentColumns = ["idPublication"],
            childColumns = ["idPublication"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteEntity(
    val email: String,
    val idPublication: Int
)
