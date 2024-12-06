package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyapp.R
import com.example.diyapp.data.adapter.explore.FeedExplore
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PublicationDetailViewModel : ViewModel() {

    val publication = MutableLiveData<FeedExplore>()
    val isAddedToFavorites = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Int?>()
    val useCases = UseCases()

    fun loadPublicationInfo(item: FeedExplore) {
        publication.value = item
    }

    fun addFavoritePublication(idPublication: Int, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = useCases.addFavoritePublication(idPublication, email)
            if (response.message.isNotEmpty()) {
                isAddedToFavorites.postValue(true)
            } else {
                errorMessage.postValue(R.string.error2)
            }
        }
    }
}
