package com.example.diyapp.data.adapter.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("correo") val email: String,
    @SerializedName("nombre") val name: String,
    @SerializedName("apellido") val lastname: String,
    @SerializedName("contra") val password: String,
    @SerializedName("foto_perfil") val userPhoto: String,
) : Parcelable {
}