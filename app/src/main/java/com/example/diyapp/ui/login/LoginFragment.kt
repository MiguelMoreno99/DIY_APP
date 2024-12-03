package com.example.diyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.data.adapter.user.User
import com.example.diyapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
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
        loginButton.setOnClickListener {
            if (validateLogin()) {
                findNavController().navigate(R.id.exploreFragment)
            } else {
                Toast.makeText(context, getString(R.string.wrongCredentials), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateLogin(): Boolean {
        val user = User(
            email = "Pedro@example.com",
            name = "Miguel Angel",
            lastname = "Davila",
            password = "12345",
            userPhoto = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAIAAAD/gAIDAAACH0lEQVR4nO2cS27DMAwF26L3v3K6IGBkUcQaifp6Zp+YnDwqtpDo+/X6kkJ+ZhewE8oCKAugLICyAMoCKAugLICyAL+zCwgKHyO++1Zxx0RZFc9Z7y+ZIG68rKxn0et9xlkbJqvf8/o4awNkDdvWiAt1VNZV1pTdn47K+t06zN0n63L1HslaZDsxP2LpyVrE1EVmPbmyVjMVpFWVNYZrarrIGcmUZC1u6qK1znZZu5gKmqptlLWXqaC+ZrdoAC2ydoxVUFl5tax9TQU19dfJ2t1UgLtwzQJUyDojVgHrhco6yVQAOnIMAUjWebEKSvsyWYByWafGKijqzmQBlAUolHX2DAb3PZosgLIAJbKeMIPBTacmC6AsgLIAt7Kes2AFn/o1WQBlAZQFUBZAWQBlAZQFUBZAWQBlAZQFUBbgVtbkf60N51O/JgugLICyACWynrNs3XRqsgDKAhTKesIk3vdosgDKApTLOnsSi7ozWQAk69RwlfZlsgBU1nnhAh1VJOskX6wXxxBQJ+uMcOEuqpO1u6+a+lvGcF9flZW7ZgEaZe0Yrvqa25O1l6+malPGcBdfrXVmHdwTdSz7a92cjzN3gV8zYmlVpX8bruYrs54ex9gtMpL5H1u/+6y5Eety9a5Hb06J2K7nlAbDlJ1wAm5wdZJu7cCzlS+yrD3i1O6L9249Dx6w2t3Z/7hFA1AWQFkAZQGUBVAWQFkAZQH+"
        )
        context?.let { SessionManager.setUserLoggedIn(it, true, user.email, user.name, user.lastname, user.userPhoto) }
        Toast.makeText(context, getString(R.string.loginSuccessful), Toast.LENGTH_SHORT).show()
        return true
    }
}