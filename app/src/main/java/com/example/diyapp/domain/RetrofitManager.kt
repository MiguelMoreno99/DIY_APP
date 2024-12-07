package com.example.diyapp.domain

import android.util.Log
import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitManager @Inject constructor(private val apiService: APIService) {

    suspend fun getFeedExplore(): List<FeedExplore> {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService.getFeedExplore()
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getFeedFavorite(email: String): List<FeedFavorites> {
        return withContext(Dispatchers.IO) {
            try {
                val user = UserEmail(email)
                val call = apiService.getFeedFavorites(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getFeedCreations(email: String): List<FeedCreations> {
        return withContext(Dispatchers.IO) {
            try {
                val user = UserEmail(email)
                val call = apiService.getFeedCreations(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getUser(email: String): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                val user = UserEmail(email)
                val call = apiService.listUser(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun editUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                val user = User(email, name, lastname, password, userPhoto)
                val call = apiService.modifyUser(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun registerUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ): ServerResponse {

        return withContext(Dispatchers.IO) {
            try {
                val user = User(
                    email,
                    name,
                    lastname,
                    password,
                    userPhoto
                )
                val call = apiService.insertUser(user)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
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
        return withContext(Dispatchers.IO) {
            try {
                val creation = UserNewPublication(
                    email,
                    title,
                    theme,
                    photoMain,
                    instructions,
                    description,
                    0,
                    photoProcess
                )
                val call = apiService.createPublication(creation)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
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
        return withContext(Dispatchers.IO) {
            try {
                val creation = UserEditPublication(
                    idPublication,
                    email,
                    title,
                    theme,
                    photoMain,
                    description,
                    instructions,
                    photoProcess
                )
                val call = apiService.editCreation(creation)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
    }

    suspend fun deletePublication(idPublication: Int, email: String): ServerResponse {
        return withContext(Dispatchers.IO) {
            try {
                val idResponse = IdResponse(idPublication, email)
                val call = apiService.deleteCreation(idResponse)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
    }

    suspend fun removeFavorite(idPublication: Int, email: String): ServerResponse {
        return withContext(Dispatchers.IO) {
            try {
                val idResponse = IdResponse(idPublication, email)
                val call = apiService.deleteFromFavorites(idResponse)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
    }

    suspend fun addFavoritePublication(idPublication: Int, email: String): ServerResponse {
        return withContext(Dispatchers.IO) {
            try {
                val idResponse = IdResponse(idPublication, email)
                val call = apiService.addToFavorites(idResponse)
                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")
                responseBody ?: ServerResponse("")
            } catch (e: Exception) {
                ServerResponse("")
            }
        }
    }
}