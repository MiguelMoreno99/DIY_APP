package com.example.diyapp.ui.login

import RetrofitManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.adapter.response.UserEmail
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.FragmentLoginBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
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
                validateUser(email, password)
                true
            }
        }
    }

    private fun validateUser(
        email: String,
        password: String
    ) {
        lifecycleScope.launch {
            val isUserAvailable = listUser(email, password)
            if (isUserAvailable) {
                SessionManager.showToast(requireContext(), R.string.loginSuccessful)
                findNavController().navigate(R.id.exploreFragment)
            } else {
                SessionManager.showToast(requireContext(), R.string.wrongCredentials)
            }
        }
    }

    private suspend fun listUser(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val userEmail = UserEmail(email)
                val call =
                    RetrofitManager.getRetroFit().create(APIService::class.java).listUser(userEmail)
                if (call.isSuccessful) {
                    val responseBody = call.body()
                    if (!responseBody.isNullOrEmpty()) {
                        if (responseBody[0].email == email && responseBody[0].password == password) {
                            SessionManager.setUserLoggedIn(
                                requireContext(),
                                true,
                                responseBody[0].email,
                                responseBody[0].name,
                                responseBody[0].lastname,
                                responseBody[0].password,
                                responseBody[0].userPhoto
                            )
                            true
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        SessionManager.showToast(requireContext(), R.string.error2)
                    }
                    false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(requireContext(), R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
                false
            }
        }
    }

}