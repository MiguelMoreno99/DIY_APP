package com.example.diyapp.domain

import android.util.Log
import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.data.adapter.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private val baseUrl = "http://myprojectapi.com/ProyectoSistemasMoviles/index.php/"
    private val retrofitInstance: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetroFit(): Retrofit {
        return retrofitInstance
    }

    suspend fun getFeedExplore(): List<FeedExplore> {
        return withContext(Dispatchers.IO) {
            val call = getRetroFit().create(APIService::class.java).getFeedExplore()
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: emptyList()
        }
    }

    suspend fun getFeedFavorite(email: String): List<FeedFavorites> {
        return withContext(Dispatchers.IO) {
            val user = UserEmail(email)
            val call = getRetroFit().create(APIService::class.java).getFeedFavorites(user)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: emptyList()
        }
    }

    suspend fun getFeedCreations(email: String): List<FeedCreations> {
        return withContext(Dispatchers.IO) {
            val user = UserEmail(email)
            val call = getRetroFit().create(APIService::class.java).getFeedCreations(user)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: emptyList()
        }
    }

    suspend fun getUser(email: String): List<User> {
        return withContext(Dispatchers.IO) {
            val user = UserEmail(email)
            val call = getRetroFit().create(APIService::class.java).listUser(user)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: emptyList()
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
            val user = User(email, name, lastname, password, userPhoto)
            val call = getRetroFit().create(APIService::class.java).modifyUser(user)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: emptyList()
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
            val user = User(
                email,
                name,
                lastname,
                password,
                userPhoto
            )
            val call = getRetroFit().create(APIService::class.java).insertUser(user)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: ServerResponse("")
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
            val call = getRetroFit().create(APIService::class.java).createPublication(creation)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: ServerResponse("")
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
            val call = getRetroFit().create(APIService::class.java).editCreation(creation)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: ServerResponse("")
        }
    }

    suspend fun deletePublication(idPublication: Int, email: String): ServerResponse {
        return withContext(Dispatchers.IO) {
            val idResponse = IdResponse(idPublication, email)
            val call = getRetroFit().create(APIService::class.java).deleteCreation(idResponse)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: ServerResponse("")
        }
    }

    suspend fun removeFavorite(idPublication: Int, email: String): ServerResponse {
        return withContext(Dispatchers.IO) {
            val idResponse = IdResponse(idPublication, email)
            val call = getRetroFit().create(APIService::class.java).deleteFromFavorites(idResponse)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: ServerResponse("")
        }
    }

    suspend fun addFavoritePublication(idPublication: Int, email: String): ServerResponse {
        return withContext(Dispatchers.IO) {
            val idResponse = IdResponse(idPublication, email)
            val call = getRetroFit().create(APIService::class.java).addToFavorites(idResponse)
            val responseBody = call.body()
            Log.d("API Response", "Server Response: $responseBody")
            responseBody ?: ServerResponse("")
        }
    }
}