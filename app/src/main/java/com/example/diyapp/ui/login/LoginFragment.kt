package com.example.diyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.databinding.FragmentLoginBinding
import com.example.diyapp.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                saveUserSession()
                findNavController().navigate(R.id.exploreFragment)
            } else {
                SessionManager.showToast(requireContext(), R.string.wrongCredentials)
            }
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterAccountActivity()
            )
        }

        binding.loginButton.setOnClickListener {
            validateLogin()
        }
    }

    private fun validateLogin() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            SessionManager.showToast(requireContext(), R.string.fillFields)
            return
        }

        lifecycleScope.launch {
            viewModel.validateUser(email, password)
        }
    }

    private fun saveUserSession() {
        lifecycleScope.launch {
            val email = binding.emailEditText.text.toString().trim()
            val user = viewModel.getUserData(email)
            SessionManager.setUserLoggedIn(
                requireContext(),
                true,
                user.email,
                user.name,
                user.lastname,
                user.password,
                user.userPhoto
            )
            SessionManager.showToast(requireContext(), R.string.loginSuccessful)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}