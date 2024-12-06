package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.launch

class FavoriteDetailViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val theme = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val instructions = MutableLiveData<String>()
    val mainPhoto = MutableLiveData<String>()
    val photoProcess = MutableLiveData<List<String>>()
    val isFavoriteRemoved = MutableLiveData<Boolean>()
    val useCases = UseCases()

    fun loadPublicationDetails(item: FeedFavorites) {
        title.value = item.title
        theme.value = item.theme
        description.value = item.description
        instructions.value = item.instructions
        mainPhoto.value = item.photoMain
        photoProcess.value = item.photoProcess
    }

    fun removeFavorite(idPublication: Int, email: String) {
        viewModelScope.launch {
            val response = useCases.removeFavorite(idPublication, email)
            isFavoriteRemoved.value = response.message.isNotEmpty()
        }
    }
}