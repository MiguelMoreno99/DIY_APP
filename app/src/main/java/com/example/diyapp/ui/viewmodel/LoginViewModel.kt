package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.domain.UseCases

class LoginViewModel : ViewModel() {

    val useCases = UseCases()
    val loginSuccess = MutableLiveData<Boolean>()

    suspend fun validateUser(email: String, password: String) {
        val response = useCases.getUser(email)
        if (response.isNotEmpty() && response[0].password == password) {
            loginSuccess.postValue(true)
        } else {
            loginSuccess.postValue(false)
        }
    }

    suspend fun getUserData(email: String): User {
        return useCases.getUser(email)[0]
    }
}