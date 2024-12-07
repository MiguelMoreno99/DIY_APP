package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyapp.data.adapter.favorites.FeedFavorites
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    val favorites = MutableLiveData<List<FeedFavorites>>()
    val isLoading = MutableLiveData<Boolean>()
    val emptyState = MutableLiveData<Boolean>()

    fun loadFavorites(email: String) {
        isLoading.value = true
        viewModelScope.launch {
            val response = useCases.getFeedFavorite(email)
            favorites.value = response
            isLoading.value = false
            emptyState.value = response.isEmpty()
        }
    }

    fun filterFavorites(query: String) {
        favorites.value = favorites.value?.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.theme.contains(query, ignoreCase = true)
        }
    }
}