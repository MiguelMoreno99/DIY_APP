package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    val useCases: UseCases
) : ViewModel() {

    val feed = MutableLiveData<List<FeedExplore>>()
    val isLoading = MutableLiveData<Boolean>()
    val showNoPublicationsMessage = MutableLiveData<Boolean>()

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