package com.example.diyapp.ui.manageaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.databinding.FragmentManageAccountsBinding
import com.example.diyapp.ui.explore.MainActivity

class ManageAccountsFragment : Fragment() {
    private lateinit var logOutButton: Button
    private lateinit var updateInfoButton: Button
    private lateinit var updatePhotoButton: Button
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

        logOutButton = view.findViewById(R.id.logoutButton)
        updateInfoButton = view.findViewById(R.id.applyChangesButton)
        updatePhotoButton = view.findViewById(R.id.changePhotoButton)

        logOutButton.setOnClickListener {
            (activity as MainActivity).setUserLoggedIn(false, "")
            findNavController().navigate(R.id.exploreFragment)
            Toast.makeText(context, "LogOut Successful", Toast.LENGTH_SHORT).show()
        }
    }
}