package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreationDetailViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    val operationResult = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun deletePublication(idPublication: Int, email: String) {
        isLoading.value = true
        viewModelScope.launch {
            val response = useCases.deletePublication(idPublication, email)
            operationResult.value = if (response.message.isNotEmpty()) {
                "PublicationDeleted"
            } else {
                "Error"
            }
            isLoading.value = false
        }
    }

    fun editPublication(
        idPublication: Int,
        email: String,
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ) {
        isLoading.value = true
        viewModelScope.launch {
            val response = useCases.editPublication(
                idPublication,
                email,
                title,
                theme,
                photoMain,
                description,
                instructions,
                photoProcess
            )
            operationResult.value = if (response.message.isNotEmpty()) {
                "PublicationEdited"
            } else {
                "Error"
            }
            isLoading.value = false
        }
    }
}