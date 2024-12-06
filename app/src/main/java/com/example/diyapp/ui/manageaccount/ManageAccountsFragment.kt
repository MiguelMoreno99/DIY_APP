package com.example.diyapp.ui.manageaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.databinding.FragmentManageAccountsBinding
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.launch

class ManageAccountsFragment : Fragment() {
    private var _binding: FragmentManageAccountsBinding? = null
    private val binding get() = _binding!!
    private var useCases: UseCases = UseCases()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageAccountsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = SessionManager.getUserInfo(requireContext())
        val name = sharedPref["name"]
        val lastname = sharedPref["lastname"]
        val profilePicture = sharedPref["photo"]

        with(binding) {
            nameEditText.setText(name)
            lastNameEditText.setText(lastname)
            profileImageView.setImageBitmap(ImageUtils.base64ToBitmap(profilePicture!!))
        }

        binding.logoutButton.setOnClickListener {
            SessionManager.setUserLoggedIn(requireContext(), false)
            findNavController().navigate(R.id.exploreFragment)
            SessionManager.showToast(requireContext(), R.string.logoutSuccessful)
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let {
                    binding.profileImageView.setImageURI(it)
                }
            }

        binding.changePhotoButton.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.applyChangesButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields(): Boolean {
        val user = SessionManager.getUserInfo(requireContext())
        val name = binding.nameEditText.text.toString().trim()
        val lastname = binding.lastNameEditText.text.toString().trim()
        val newPassword = binding.newPasswordEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
        val photo = ImageUtils.bitmapToBase64(binding.profileImageView.drawToBitmap())

        when {
            name.isBlank() || lastname.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                SessionManager.showToast(requireContext(), R.string.fillFields)
                return false
            }

            password != confirmPassword -> {
                SessionManager.showToast(requireContext(), R.string.differentPasswords)
                return false
            }

            password != user["password"].toString() -> {
                SessionManager.showToast(requireContext(), R.string.verifyPassword)
                return false
            }

            else -> {
                if (newPassword.isBlank()) {
                    lifecycleScope.launch {
                        editUser(
                            user["email"].toString(),
                            name,
                            lastname,
                            user["password"].toString(),
                            photo
                        )
                    }
                    navigateToExploreFragment()
                    return true
                } else if (SessionManager.isValidPassword(newPassword)) {
                    lifecycleScope.launch {
                        editUser(user["email"].toString(), name, lastname, newPassword, photo)
                    }
                    navigateToExploreFragment()
                    return true
                } else {
                    SessionManager.showToast(requireContext(), R.string.checkPassword)
                    return false
                }
            }
        }
    }

    private fun navigateToExploreFragment() {
        findNavController().navigate(R.id.exploreFragment)
    }

    private suspend fun editUser(
        email: String,
        name: String,
        lastname: String,
        password: String,
        userPhoto: String
    ) {
        val response = useCases.editUser(
            email,
            name,
            lastname,
            password,
            userPhoto
        )
        if (response.isNotEmpty()) {
            SessionManager.showToast(
                requireContext(),
                R.string.userUpdated
            )
            SessionManager.setUserLoggedIn(
                requireContext(),
                true,
                email,
                name,
                lastname,
                password,
                userPhoto
            )
        } else {
            SessionManager.showToast(
                requireContext(),
                R.string.error2
            )
        }
    }
}