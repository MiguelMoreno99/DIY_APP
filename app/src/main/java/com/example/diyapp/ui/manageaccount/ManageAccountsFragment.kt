package com.example.diyapp.ui.manageaccount

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.databinding.FragmentManageAccountsBinding
import com.example.diyapp.ui.explore.MainActivity

class ManageAccountsFragment : Fragment() {
    private lateinit var logOutButton: Button
    private lateinit var updateInfoButton: Button
    private lateinit var updatePhotoButton: Button
    private lateinit var ivImage: ImageView
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
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

        ivImage = view.findViewById(R.id.profileImageView)
        updatePhotoButton = view.findViewById(R.id.changePhotoButton)
        etName = view.findViewById(R.id.nameEditText)
        etLastName = view.findViewById(R.id.lastNameEditText)
        etPassword = view.findViewById(R.id.passwordEditText)
        etConfirmPassword = view.findViewById(R.id.confirmPasswordEditText)
        updateInfoButton = view.findViewById(R.id.applyChangesButton)
        logOutButton = view.findViewById(R.id.logoutButton)

        val sharedPref =
            requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", "")
        val lastname = sharedPref.getString("lastname", "")
        val profilePicture = sharedPref.getString("photo", "")

        etName.setText(name)
        etLastName.setText(lastname)
        val photoMainBytes = Base64.decode(profilePicture, Base64.DEFAULT)
        val photoMainBitmap =
            BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
        ivImage.setImageBitmap(photoMainBitmap)

        logOutButton.setOnClickListener {
            (activity as MainActivity).setUserLoggedIn(false, "", "", "", "")
            findNavController().navigate(R.id.exploreFragment)
            Toast.makeText(context, "LogOut Successful", Toast.LENGTH_SHORT).show()
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    ivImage.setImageURI(uri)
                }
            }

        updatePhotoButton.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        updateInfoButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val name = etName.text.toString()
        val lastname = etLastName.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()
        if (name == "" || lastname == "" || password == "" || confirmPassword == ""){
            Toast.makeText(requireContext(), "Fill all the fields First!", Toast.LENGTH_SHORT).show()
        }else if(password != confirmPassword){
            Toast.makeText(requireContext(), "Please Verify Passwords!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "User Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.exploreFragment)
        }
    }
}