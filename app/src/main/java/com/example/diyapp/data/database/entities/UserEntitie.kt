package com.example.diyapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diyapp.data.model.UserModel

@Entity(tableName = "userTable")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "userPhoto") val userPhoto: String
)

fun UserModel.toDatabase() = UserEntity(email, name, lastname, password, userPhoto)