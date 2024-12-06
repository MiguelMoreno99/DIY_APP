package com.example.diyapp.data

import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.creations.FeedCreationsProvider
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.data.adapter.explore.FeedExploreProvider
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.favorites.FeedFavoritesProvider
import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.data.adapter.user.UserProvider
import com.example.diyapp.domain.RetrofitManager
import com.example.diyapp.domain.ServerResponse

class MainRepository {

    private val api = RetrofitManager()

    suspend fun getFeedExplore(): List<FeedExplore> {
        val response = api.getFeedExplore()
        FeedExploreProvider.feedExploreList = response
        return response
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

    suspend fun getUser(email: String): List<User> {
        val response = api.getUser(email)
        UserProvider.UserData = response
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
        UserProvider.UserData = response
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