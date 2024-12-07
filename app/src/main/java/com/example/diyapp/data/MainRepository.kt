package com.example.diyapp.data

import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.creations.FeedCreationsProvider
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.favorites.FeedFavoritesProvider
import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.data.database.daos.CreationsDao
import com.example.diyapp.data.database.daos.FavoriteDao
import com.example.diyapp.data.database.daos.UserDao
import com.example.diyapp.data.database.entities.CreationEntity
import com.example.diyapp.data.database.entities.UserEntity
import com.example.diyapp.data.model.CreationModel
import com.example.diyapp.data.model.UserModel
import com.example.diyapp.data.model.toDomain
import com.example.diyapp.domain.RetrofitManager
import com.example.diyapp.domain.ServerResponse
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: RetrofitManager,
    private val creationsDao: CreationsDao,
    private val favoritesDao: FavoriteDao,
    private val userDao: UserDao
) {

    suspend fun getFeedExploreFromApi(): List<CreationModel> {
        val response: List<FeedExplore> = api.getFeedExplore()
        return response.map { it.toDomain() }
    }

    suspend fun getUserFromApi(email: String): List<UserModel> {
        val response: List<User> = api.getUser(email)
        return response.map { it.toDomain() }
    }

    suspend fun getFeedExploreFromDataBase(): List<CreationModel> {
        val response: List<CreationEntity> = creationsDao.getAllCompletedPublications()
        return response.map { it.toDomain() }
    }

    suspend fun getUserFromDataBase(email: String): List<UserModel> {
        val response: List<UserEntity> = userDao.getUserInfo(email)
        return response.map { it.toDomain() }
    }

    suspend fun insertUsers(user: List<UserEntity>) {
        userDao.insertUser(user)
    }

    suspend fun insertCreations(creation: List<CreationEntity>) {
        creationsDao.insertPublication(creation)
    }

    suspend fun clearUsers() {
        userDao.deleteAllUsers()
    }

    suspend fun clearPublications() {
        creationsDao.deleteAllPublications()
    }

    suspend fun getFeedFavorite(email: String): List<FeedFavorites> {
        val response = api.getFeedFavorite(email)
        FeedFavoritesProvider.feedFavoritesList = response
        return response
    }

    suspend fun getFeedCreations(email: String): List<FeedCreations> {
        val response = api.getFeedCreations(email)
        FeedCreationsProvider.feedCreationsList = response
        return response
    }

    suspend fun editUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ): List<User> {
        val response = api.editUser(email, name, lastname, password, userPhoto)
        return response
    }

    suspend fun registerUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ): ServerResponse {
        val response = api.registerUser(email, name, lastname, password, userPhoto)
        return response
    }

    suspend fun createPublication(
        email: String,
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ): ServerResponse {
        val response = api.createPublication(
            email,
            title,
            theme,
            photoMain,
            description,
            instructions,
            photoProcess
        )
        return response
    }

    suspend fun editPublication(
        idPublication: Int,
        email: String,
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ): ServerResponse {
        val response = api.editPublication(
            idPublication,
            email,
            title,
            theme,
            photoMain,
            description,
            instructions,
            photoProcess
        )
        return response
    }

    suspend fun deletePublication(idPublication: Int, email: String): ServerResponse {
        val response = api.deletePublication(idPublication, email)
        return response
    }

    suspend fun removeFavorite(idPublication: Int, email: String): ServerResponse {
        val response = api.removeFavorite(idPublication, email)
        return response
    }

    suspend fun addFavoritePublication(idPublication: Int, email: String): ServerResponse {
        val response = api.addFavoritePublication(idPublication, email)
        return response
    }
}