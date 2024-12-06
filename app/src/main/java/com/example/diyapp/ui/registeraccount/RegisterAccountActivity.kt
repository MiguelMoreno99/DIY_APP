package com.example.diyapp.ui.registeraccount

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.databinding.ActivityRegisterAccountBinding
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.launch

class RegisterAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAccountBinding
    private var imageSet = false
    private var useCases: UseCases = UseCases()
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
                lifecycleScope.launch {
                    validateUser(email, name, lastname, password, imageBlob)
                }
                true
            }
        }
    }

    private suspend fun validateUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        imageBlob: String
    ) {
        val response = useCases.getUser(email)
        if (response.isEmpty()) {
            registerUser(email, name, lastname, password, imageBlob)
            finish()
        } else {
            SessionManager.showToast(this, R.string.userAlreadyExists)
        }
    }

    private suspend fun registerUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ) {
        val response = useCases.registerUser(
            email,
            name,
            lastname,
            password,
            userPhoto
        )
        if (response.message.isNotEmpty()) {
            SessionManager.showToast(
                this,
                R.string.userCreated
            )
        } else {
            SessionManager.showToast(
                this,
                R.string.error2
            )
        }
    }
}