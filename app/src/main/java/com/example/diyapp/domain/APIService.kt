package com.example.diyapp.domain

import com.example.diyapp.data.adapter.explore.feedExplore
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getFeedByTheme(@Url url: String): Response<feedExplore>
}