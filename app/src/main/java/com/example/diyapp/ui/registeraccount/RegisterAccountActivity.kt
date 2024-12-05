package com.example.diyapp.ui.registeraccount

import RetrofitManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.response.ServerResponse
import com.example.diyapp.data.adapter.response.UserEmail
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.databinding.ActivityRegisterAccountBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAccountBinding
    private var imageSet = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.profileImageView.setImageURI(uri)
                    imageSet = true
                }
            }

        binding.changePhotoButton.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.registerButton.setOnClickListener {
            validateFields()
        }

    }

    private fun validateFields(): Boolean {

        val email = binding.mailEditText.text.toString().trim()
        val name = binding.nameEditText.text.toString().trim()
        val lastname = binding.lastNameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
        val image = binding.profileImageView.drawToBitmap()
        val imageBlob = ImageUtils.bitmapToBase64(image)

        return when {

            email.isEmpty() || name.isEmpty() || lastname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || !imageSet -> {
                SessionManager.showToast(this, R.string.fillFields)
                false
            }

            !SessionManager.validateInputs(this, email, password) -> {
                false
            }

            password != confirmPassword -> {
                SessionManager.showToast(this, R.string.differentPasswords)
                false
            }

            else -> {
                validateUser(email, name, lastname, password, imageBlob)
                true
            }
        }
    }

    private fun validateUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        imageBlob: String
    ) {
        lifecycleScope.launch {
            val isUserAvailable = listUser(email)
            if (isUserAvailable) {
                registerUser(email, name, lastname, password, imageBlob)
            } else {
                SessionManager.showToast(this@RegisterAccountActivity, R.string.userAlreadyExists)
            }
        }
    }

    private fun registerUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ) {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val user = User(
                    email,
                    name,
                    lastname,
                    password,
                    userPhoto
                )
                val call = RetrofitManager.getRetroFit().create(APIService::class.java)
                    .insertUser(user)

                val responseBody: ServerResponse = call.body()!!

                withContext(Dispatchers.Main) {

                    if (call.isSuccessful) {
                        if (responseBody.message.isNotEmpty()) {

                            if (responseBody.message == "Usuario agregado exitosamente.") {
                                SessionManager.showToast(
                                    this@RegisterAccountActivity,
                                    R.string.userCreated
                                )
                                withContext(Dispatchers.Main) {
                                    finish()
                                }
                            } else {
                                SessionManager.showToast(
                                    this@RegisterAccountActivity,
                                    R.string.userAlreadyExists
                                )
                            }
                        }
                    } else {
                        SessionManager.showToast(this@RegisterAccountActivity, R.string.error2)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(this@RegisterAccountActivity, R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }

    private suspend fun listUser(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val userEmail = UserEmail(email)
                val call =
                    RetrofitManager.getRetroFit().create(APIService::class.java).listUser(userEmail)

                if (call.isSuccessful) {
                    val responseBody = call.body()
                    if (!responseBody.isNullOrEmpty()) {
                        responseBody[0].email != email
                    } else {
                        true
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        SessionManager.showToast(this@RegisterAccountActivity, R.string.error2)
                    }
                    false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(this@RegisterAccountActivity, R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
                false
            }
        }
    }


}