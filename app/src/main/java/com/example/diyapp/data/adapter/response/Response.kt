package com.example.diyapp.data.adapter.response

import com.google.gson.annotations.SerializedName

data class ServerResponse(
    @SerializedName("message") val message: String
)

data class IdResponse(
    @SerializedName("id_publicacion") val id: Int,
    @SerializedName("correo") val email: String
)

data class UserCredentials(
    @SerializedName("correo") val email: String,
    @SerializedName("contra") val password: String
)

data class UserEmail(
    @SerializedName("correo") val email: String
)

data class UserNewPublication(
    @SerializedName("correo") val email: String,
    @SerializedName("titulo") val title: String,
    @SerializedName("nombre_tema") val theme: String,
    @SerializedName("foto_portada") val photoMain: String,
    @SerializedName("instrucciones") val instructions: String,
    @SerializedName("descripcion") val description: String,
    @SerializedName("num_likes") val numLikes: Int,
    @SerializedName("foto_proceso") val photoProcess: List<String>
)

data class UserEditPublication(
    @SerializedName("id_publicacion") val id: Int,
    @SerializedName("correo") val email: String,
    @SerializedName("titulo") val title: String,
    @SerializedName("nombre_tema") val theme: String,
    @SerializedName("foto_portada") val photoMain: String,
    @SerializedName("descripcion") val description: String,
    @SerializedName("instrucciones") val instructions: String,
    @SerializedName("foto_proceso") val photoProcess: List<String>
)