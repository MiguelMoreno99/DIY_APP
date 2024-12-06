package com.example.diyapp.domain

import com.example.diyapp.data.MainRepository
import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.user.User
import javax.inject.Inject

class UseCases @Inject constructor(private val repository: MainRepository) {

    suspend fun getFeedExplore(): List<FeedExplore> {
        return repository.getFeedExplore()
    }

    suspend fun getFeedFavorite(email: String): List<FeedFavorites> {
        return repository.getFeedFavorite(email)
    }

    suspend fun getFeedCreations(email: String): List<FeedCreations> {
        return repository.getFeedCreations(email)
    }

    suspend fun getUser(email: String): List<User> {
        return repository.getUser(email)
    }

    suspend fun editUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ): List<User> {
        return repository.editUser(email, name, lastname, password, userPhoto)
    }

    suspend fun registerUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ): ServerResponse {
        return repository.registerUser(email, name, lastname, password, userPhoto)
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
        return repository.createPublication(
            email,
            title,
            theme,
            photoMain,
            description,
            instructions,
            photoProcess
        )
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
        return repository.editPublication(
            idPublication,
            email,
            title,
            theme,
            photoMain,
            description,
            instructions,
            photoProcess
        )
    }

    suspend fun deletePublication(idPublication: Int, email: String): ServerResponse {
        return repository.deletePublication(idPublication, email)
    }

    suspend fun removeFavorite(idPublication: Int, email: String): ServerResponse {
        return repository.removeFavorite(idPublication, email)
    }

    suspend fun addFavoritePublication(idPublication: Int, email: String): ServerResponse {
        return repository.addFavoritePublication(idPublication, email)
    }
}