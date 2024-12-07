package com.example.diyapp.data.model

import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.data.database.entities.UserEntity

data class UserModel(
    val email: String,
    val name: String,
    val lastname: String,
    val password: String,
    val userPhoto: String,
)

fun User.toDomain() = UserModel(email, name, lastname, password, userPhoto)
fun UserEntity.toDomain() = UserModel(email, name, lastname, password, userPhoto)
