package com.example.diyapp.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.databinding.FragmentLoginBinding
import com.example.diyapp.ui.explore.MainActivity

class LoginFragment : Fragment() {
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    private lateinit var email: String
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton = view.findViewById(R.id.registerButton)
        loginButton = view.findViewById(R.id.loginButton)

        registerButton.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterAccountActivity()
            )
        }
        email = ""
        loginButton.setOnClickListener {
            if (validateLogin()) {
                (activity as MainActivity).setUserLoggedIn(true,email)
                findNavController().navigate(R.id.exploreFragment)
            } else {
                Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validateLogin(): Boolean {
        email = "usuario@example.com"
        Toast.makeText(context, "LogIn Successful with: $email", Toast.LENGTH_SHORT).show()
        return true
    }
}