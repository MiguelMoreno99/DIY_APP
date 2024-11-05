package com.example.diyapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diyapp.R
import com.example.diyapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var registerButton: Button
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

        registerButton.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterAccountActivity()
            )
        }
    }
}