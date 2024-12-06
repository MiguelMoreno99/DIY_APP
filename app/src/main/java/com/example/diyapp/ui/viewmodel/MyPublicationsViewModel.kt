package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diyapp.R
import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.domain.UseCases

class MyPublicationsViewModel : ViewModel() {

    val useCases = UseCases()
    val feedCreations = MutableLiveData<List<FeedCreations>>()
    val errorMessage = MutableLiveData<Int?>()

    suspend fun loadFeedCreations(email: String) {
        val response = useCases.getFeedCreations(email)
        if (response.isNotEmpty()) {
            feedCreations.postValue(response)
            errorMessage.postValue(null)
        } else {
            errorMessage.postValue(R.string.notHavePublications)
        }
    }
}