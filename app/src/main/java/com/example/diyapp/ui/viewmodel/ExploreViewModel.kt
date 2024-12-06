package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    val feed = MutableLiveData<List<FeedExplore>>()
    val isLoading = MutableLiveData<Boolean>()
    val showNoPublicationsMessage = MutableLiveData<Boolean>()
    val useCases = UseCases()

    fun loadFeed() {
        isLoading.value = true
        viewModelScope.launch {
            val response = useCases.getFeedExplore()
            isLoading.value = false
            if (response.isNotEmpty()) {
                feed.value = response
                showNoPublicationsMessage.value = false
            } else {
                showNoPublicationsMessage.value = true
            }
        }
    }
}