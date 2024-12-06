package com.example.diyapp.domain

import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @GET("publicacion/list/")
    suspend fun getFeedExplore(): Response<List<FeedExplore>> //

    @POST("publicacion/listPorUsuario/")
    suspend fun getFeedCreations(@Body userEmail: UserEmail): Response<List<FeedCreations>> //

    @POST("favorito/list/")
    suspend fun getFeedFavorites(@Body userEmail: UserEmail): Response<List<FeedFavorites>> //

    @POST("user/listUser/")
    suspend fun listUser(@Body userEmail: UserEmail): Response<List<User>> //

    @POST("publicacion/modify/")
    suspend fun editCreation(@Body creation: UserEditPublication): Response<ServerResponse> //

    @POST("publicacion/insertPublicacion/")
    suspend fun createPublication(@Body creation: UserNewPublication): Response<ServerResponse> //

    @POST("user/modify/")
    suspend fun modifyUser(@Body user: User): Response<List<User>> //

    @POST("user/insert/")
    suspend fun insertUser(@Body user: User): Response<ServerResponse> //

    @POST("publicacion/eliminar/")
    suspend fun deleteCreation(@Body idPublication: IdResponse): Response<ServerResponse> //

    @POST("favorito/insertFavorito/")
    suspend fun addToFavorites(@Body idPublication: IdResponse): Response<ServerResponse> //

    @POST("favorito/eliminar/")
    suspend fun deleteFromFavorites(@Body idPublication: IdResponse): Response<ServerResponse> //
}