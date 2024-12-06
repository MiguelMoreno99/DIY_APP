package com.example.diyapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diyapp.R
import com.example.diyapp.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewPublicationViewModel @Inject constructor(
    val useCases: UseCases
) : ViewModel() {

    var email: String = ""
    val isPublicationCreated = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Int?>()

    fun setUserEmail(userEmail: String) {
        email = userEmail
    }

    suspend fun createPublication(
        title: String,
        description: String,
        theme: String,
        instructions: String,
        mainPhoto: String,
        photoProcess: List<String>
    ) {
        if (validateFields(title, description, instructions, mainPhoto, photoProcess)) {
            val response = useCases.createPublication(
                email,
                title,
                theme,
                mainPhoto,
                description,
                instructions,
                photoProcess
            )
            isPublicationCreated.postValue(response.message.isNotEmpty())
        }
    }

    private fun validateFields(
        title: String,
        description: String,
        instructions: String,
        mainPhoto: String,
        photoProcess: List<String>
    ): Boolean {
        return when {
            title.isEmpty() || description.isEmpty() || instructions.isEmpty() || mainPhoto.isEmpty() -> {
                errorMessage.postValue(R.string.fillFields)
                false
            }

            photoProcess.isEmpty() -> {
                errorMessage.postValue(R.string.fillFields)
                false
            }

            else -> true
        }
    }
}