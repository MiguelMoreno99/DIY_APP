package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diyapp.R
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterAccountViewModel @Inject constructor(
    val useCases: UseCases
) : ViewModel() {

    val errorMessage = MutableLiveData<Int?>()
    val isUserRegistered = MutableLiveData<Boolean>()

    suspend fun registerAccount(
        email: String,
        name: String,
        lastname: String,
        password: String,
        confirmPassword: String,
        imageBlob: String?
    ) {
        if (validateFields(email, name, lastname, password, confirmPassword, imageBlob)) {
            checkIfUserExists(email, name, lastname, password, imageBlob!!)
        }
    }

    private fun validateFields(
        email: String,
        name: String,
        lastname: String,
        password: String,
        confirmPassword: String,
        imageBlob: String?
    ): Boolean {
        return when {
            email.isEmpty() || name.isEmpty() || lastname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || imageBlob == null -> {
                errorMessage.postValue(R.string.fillFields)
                false
            }

            password != confirmPassword -> {
                errorMessage.postValue(R.string.differentPasswords)
                false
            }

            else -> true
        }
    }

    private suspend fun checkIfUserExists(
        email: String,
        name: String,
        lastname: String,
        password: String,
        imageBlob: String
    ) {
        val response = useCases.getUser(email)
        if (response.isEmpty()) {
            registerUser(email, name, lastname, password, imageBlob)
        } else {
            errorMessage.postValue(R.string.userAlreadyExists)
        }
    }

    private suspend fun registerUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        imageBlob: String
    ) {
        val response = useCases.registerUser(email, name, lastname, password, imageBlob)
        if (response.message.isNotEmpty()) {
            isUserRegistered.postValue(true)
        } else {
            errorMessage.postValue(R.string.error2)
        }
    }
}