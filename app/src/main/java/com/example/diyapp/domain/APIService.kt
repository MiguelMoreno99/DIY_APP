package com.example.diyapp.domain

import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.feedExplore
import com.example.diyapp.data.adapter.favorites.feedFavorites
import com.example.diyapp.data.adapter.response.IdResponse
import com.example.diyapp.data.adapter.response.ServerResponse
import com.example.diyapp.data.adapter.response.UserCredentials
import com.example.diyapp.data.adapter.response.UserEmail
import com.example.diyapp.data.adapter.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @GET("ProyectoSistemasMoviles/index.php/publicacion/list/")
    suspend fun getFeedExplore(): Response<List<feedExplore>>


    @POST("ProyectoSistemasMoviles/index.php/publicacion/listPorUsuario")
    suspend fun getFeedCreations(@Body userEmail: UserEmail): Response<List<FeedCreations>> // mandar correo
    @POST("ProyectoSistemasMoviles/index.php/favorito/list")
    suspend fun getFeedFavorites(@Body userEmail: UserEmail): Response<List<feedFavorites>> // mandar correo
    @POST("ProyectoSistemasMoviles/index.php/user/listUser")
    suspend fun fillUser(@Body userEmail: UserEmail): Response<User> // mandar correo


    @POST("ProyectoSistemasMoviles/index.php/")
    suspend fun editCreation(@Body creation: FeedCreations): Response<ServerResponse> //mandar toda la publicacion
    @POST("ProyectoSistemasMoviles/index.php/")
    suspend fun createPublication(@Body creation: FeedCreations): Response<ServerResponse> //mandar toda la publicacion
    @POST("ProyectoSistemasMoviles/index.php/user/modify")
    suspend fun modifyUser(@Body user: User): Response<User> // mandartodo el usuario
    @POST("ProyectoSistemasMoviles/index.php/user/insert")
    suspend fun insertUser(@Body user: User): Response<ServerResponse> // mandartodo el usuario


    @POST("ProyectoSistemasMoviles/index.php/user/verify")
    suspend fun verifyUser(@Body userCredentials: UserCredentials): Response<ServerResponse> // mandar correo y contra


    @POST("ProyectoSistemasMoviles/index.php/")
    suspend fun deleteCreation(@Body idPublication: IdResponse): Response<ServerResponse> //mandar id de la publicacion
    @POST("ProyectoSistemasMoviles/index.php/")
    suspend fun addToFavorites(@Body idPublication: IdResponse): Response<ServerResponse> //mandar id de la publicacion
    @POST("ProyectoSistemasMoviles/index.php/")
    suspend fun deleteFromFavorites(@Body idPublication: IdResponse): Response<ServerResponse> //mandar id de la publicacion
}