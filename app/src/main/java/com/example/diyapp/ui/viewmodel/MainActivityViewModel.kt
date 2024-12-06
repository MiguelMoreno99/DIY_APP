package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val navigationCommand = MutableLiveData<Pair<Int, Boolean>>()

    fun checkLoginAndNavigate(
        isLoggedIn: Boolean,
        destinationIfLoggedIn: Int,
        destinationIfNotLoggedIn: Int
    ) {
        if (isLoggedIn) {
            navigationCommand.value = Pair(destinationIfLoggedIn, false)
        } else {
            navigationCommand.value = Pair(destinationIfNotLoggedIn, true)
        }
    }
}