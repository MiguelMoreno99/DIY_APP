package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageAccountsViewModel @Inject constructor(
    val useCases: UseCases
) : ViewModel() {

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