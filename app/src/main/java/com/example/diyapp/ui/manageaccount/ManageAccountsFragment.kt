package com.example.diyapp.ui.manageaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.databinding.FragmentManageAccountsBinding
import com.example.diyapp.ui.viewmodel.ManageAccountsViewModel
import kotlinx.coroutines.launch

class ManageAccountsFragment : Fragment() {

    private var _binding: FragmentManageAccountsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ManageAccountsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageAccountsBinding.inflate(inflater, container, false)
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
            SessionManager.showToast(requireContext(), R.string.logoutSuccessful)
            findNavController().navigate(R.id.exploreFragment)
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let { binding.profileImageView.setImageURI(it) }
            }
        binding.changePhotoButton.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.applyChangesButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val user = SessionManager.getUserInfo(requireContext())
        val name = binding.nameEditText.text.toString().trim()
        val lastname = binding.lastNameEditText.text.toString().trim()
        val newPassword = binding.newPasswordEditText.text.toString().trim()
        val currentPassword = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
        val photo = ImageUtils.bitmapToBase64(binding.profileImageView.drawToBitmap())

        when {
            name.isBlank() || lastname.isBlank() || currentPassword.isBlank() || confirmPassword.isBlank() -> {
                SessionManager.showToast(requireContext(), R.string.fillFields)
            }

            currentPassword != confirmPassword -> {
                SessionManager.showToast(requireContext(), R.string.differentPasswords)
            }

            currentPassword != user["password"] -> {
                SessionManager.showToast(requireContext(), R.string.verifyPassword)
            }

            else -> {
                lifecycleScope.launch {
                    if (newPassword.isBlank()) {
                        viewModel.updateUser(
                            user["email"]!!, name, lastname, user["password"]!!, photo
                        )
                        SessionManager.setUserLoggedIn(
                            requireContext(),
                            true,
                            user["email"]!!,
                            name,
                            lastname,
                            user["password"]!!,
                            photo
                        )
                    } else if (SessionManager.isValidPassword(newPassword)) {
                        viewModel.updateUser(
                            user["email"]!!, name, lastname, newPassword, photo
                        )
                        SessionManager.setUserLoggedIn(
                            requireContext(),
                            true,
                            user["email"]!!,
                            name,
                            lastname,
                            newPassword,
                            photo
                        )
                    } else {
                        SessionManager.showToast(requireContext(), R.string.checkPassword)
                        return@launch
                    }
                    SessionManager.showToast(requireContext(), R.string.userUpdated)
                    findNavController().navigate(R.id.exploreFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}