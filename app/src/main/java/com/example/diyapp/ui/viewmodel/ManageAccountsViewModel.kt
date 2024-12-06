package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.diyapp.domain.UseCases

class ManageAccountsViewModel : ViewModel() {

    private val useCases = UseCases()

    suspend fun updateUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        photo: String
    ) {
        useCases.editUser(email, name, lastname, password, photo)
    }
}