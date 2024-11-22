package com.example.diyapp.domain

import com.example.diyapp.data.adapter.creations.feedCreations
import com.example.diyapp.data.adapter.explore.feedExplore
import com.example.diyapp.data.adapter.favorites.feedFavorites
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getFeedByUser(@Url url: String): Response<feedCreations>

    @GET
    suspend fun getFavoritesByUser(@Url url: String): Response<feedFavorites>

    @GET
    suspend fun getFeed(): Response<feedExplore>
}