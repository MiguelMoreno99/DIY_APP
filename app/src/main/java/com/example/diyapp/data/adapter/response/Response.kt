package com.example.diyapp.data.adapter.response

import com.google.gson.annotations.SerializedName

data class ServerResponse(
    @SerializedName("message") val message: String
)

data class IdResponse(
    @SerializedName("id") val id: Int
)

data class UserCredentials(
    @SerializedName("correo") val email: String,
    @SerializedName("contra") val password: String
)

data class UserEmail(
    @SerializedName("correo") val email: String
)