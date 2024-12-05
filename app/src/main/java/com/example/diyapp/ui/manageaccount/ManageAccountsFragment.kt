package com.example.diyapp.ui.manageaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.FragmentManageAccountsBinding

class ManageAccountsFragment : Fragment() {
    private var _binding: FragmentManageAccountsBinding? = null
    private val binding get() = _binding!!
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

    private fun validateFields() {
        val name = binding.nameEditText.text.toString()
        val lastname = binding.lastNameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        when {
            name.isBlank() || lastname.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                SessionManager.showToast(requireContext(), R.string.fillFields)
            }

            password != confirmPassword -> {
                SessionManager.showToast(requireContext(), R.string.verifyPassword)
            }

            else -> {
                SessionManager.showToast(requireContext(), R.string.userUpdated)
                navigateToExploreFragment()
            }
        }
    }

    private fun navigateToExploreFragment() {
        findNavController().navigate(R.id.exploreFragment)
    }
}