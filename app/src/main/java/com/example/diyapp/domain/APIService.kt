package com.example.diyapp.domain

import com.example.diyapp.data.adapter.creations.feedCreations
import com.example.diyapp.data.adapter.explore.feedExplore
import com.example.diyapp.data.adapter.favorites.feedFavorites
import com.example.diyapp.data.adapter.response.InsertResponse
import com.example.diyapp.data.adapter.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {
    @GET("ProyectoSistemasMoviles/index.php/publicacion/list/")
    suspend fun getFeedExplore(): Response<List<feedExplore>>

    @POST("ProyectoSistemasMoviles/index.php/favorito/list")
    suspend fun getFeedFavorites(@Body user: User): Response<List<feedFavorites>> // mandar correo

    @POST("ProyectoSistemasMoviles/index.php/publicacion/listPorUsuario")
    suspend fun getFeedCreations(@Body user: User): Response<List<feedCreations>> // mandar correo

    @POST("ProyectoSistemasMoviles/index.php/publicacion/listPorUsuario")
    suspend fun editCreation(@Body creation: feedCreations): Response<List<feedCreations>> //mandar toda la publicacion

    @POST("ProyectoSistemasMoviles/index.php/user/verify")
    suspend fun verifyUser(@Body user: User): Response<InsertResponse> // mandar correo y contra

    @POST("ProyectoSistemasMoviles/index.php/user/listUser")
    suspend fun fillUser(@Body user: User): Response<User> // mandar correo

    @POST("ProyectoSistemasMoviles/index.php/user/modify")
    suspend fun modifyUser(@Body user: User): Response<User> // mandartodo el usuario

    @POST("ProyectoSistemasMoviles/index.php/user/insert")
    suspend fun insertUsers(@Body user: User): Response<InsertResponse>
}