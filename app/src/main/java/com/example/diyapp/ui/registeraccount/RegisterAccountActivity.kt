package com.example.diyapp.ui.registeraccount

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.databinding.ActivityRegisterAccountBinding
import com.example.diyapp.ui.viewmodel.RegisterAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAccountBinding
    private var imageSet = false
    private val viewModel: RegisterAccountViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupImagePicker()
        setupListeners()
        observeViewModel()
    }

    private fun setupImagePicker() {
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
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            val email = binding.mailEditText.text.toString().trim()
            val name = binding.nameEditText.text.toString().trim()
            val lastname = binding.lastNameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            val imageBlob = if (imageSet) {
                ImageUtils.bitmapToBase64(binding.profileImageView.drawToBitmap())
            } else {
                null
            }
            lifecycleScope.launch {
                viewModel.registerAccount(
                    email,
                    name,
                    lastname,
                    password,
                    confirmPassword,
                    imageBlob
                )
            }
        }
    }

    private fun observeViewModel() {
        viewModel.errorMessage.observe(this) { messageResId ->
            messageResId?.let {
                sessionManager.showToast(this, it)
            }
        }

        viewModel.isUserRegistered.observe(this) { isRegistered ->
            if (isRegistered) {
                sessionManager.showToast(this, R.string.userCreated)
                finish()
            }
        }
    }
}