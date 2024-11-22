package com.example.diyapp.data.adapter.response

import com.google.gson.annotations.SerializedName

data class InsertResponse(
    @SerializedName("message") val message: String,
)