package com.example.diyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.databinding.FragmentLoginBinding
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var useCases: UseCases = UseCases()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterAccountActivity()
            )
        }
        binding.loginButton.setOnClickListener {
            validateLogin()
        }
    }

    private fun validateLogin(): Boolean {

        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        return when {
            email.isEmpty() -> {
                SessionManager.showToast(requireContext(), R.string.fillFields)
                false
            }

            password.isEmpty() -> {
                SessionManager.showToast(requireContext(), R.string.fillFields)
                false
            }

            else -> {
                lifecycleScope.launch {
                    validateUser(email, password)
                }
                true
            }
        }
    }

    private suspend fun validateUser(
        email: String,
        password: String
    ) {
        val response = useCases.getUser(email)
        if (response.isNotEmpty()) {
            if (response[0].password == password) {
                SessionManager.showToast(
                    requireContext(),
                    R.string.loginSuccessful
                )
                SessionManager.setUserLoggedIn(
                    requireContext(),
                    true,
                    response[0].email,
                    response[0].name,
                    response[0].lastname,
                    response[0].password,
                    response[0].userPhoto
                )
                findNavController().navigate(R.id.exploreFragment)
            } else {
                SessionManager.showToast(
                    requireContext(),
                    R.string.wrongCredentials
                )
            }
        } else {
            SessionManager.showToast(
                requireContext(),
                R.string.wrongCredentials
            )
        }
    }
}