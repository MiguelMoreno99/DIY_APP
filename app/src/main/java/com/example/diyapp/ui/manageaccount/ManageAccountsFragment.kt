package com.example.diyapp.ui.manageaccount

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.FragmentManageAccountsBinding

class ManageAccountsFragment : Fragment() {
    private var _binding: FragmentManageAccountsBinding? = null
    private val sessionManager = SessionManager
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

        val sharedPref = sessionManager.getUserInfo(requireContext())
        val name = sharedPref["name"]
        val lastname = sharedPref["lastname"]
        val profilePicture = sharedPref["photo"]

        with(binding) {
            nameEditText.setText(name)
            lastNameEditText.setText(lastname)
            sessionManager.setUserLoggedIn(requireContext(), false)
            val photoMainBytes = Base64.decode(profilePicture, Base64.DEFAULT)
            val photoMainBitmap =
                BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
            profileImageView.setImageBitmap(photoMainBitmap)
        }

        binding.logoutButton.setOnClickListener {
            SessionManager.setUserLoggedIn(requireContext(), false)
            findNavController().navigate(R.id.exploreFragment)
            showToast(R.string.logoutSuccessful)
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
                showToast(R.string.fillFields)
            }

            password != confirmPassword -> {
                showToast(R.string.verifyPassword)
            }

            else -> {
                showToast(R.string.userUpdated)
                navigateToExploreFragment()
            }
        }
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToExploreFragment() {
        findNavController().navigate(R.id.exploreFragment)
    }
}