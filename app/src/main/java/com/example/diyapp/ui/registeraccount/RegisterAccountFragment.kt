package com.example.diyapp.ui.registeraccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.diyapp.databinding.FragmentRegisterAccountBinding

class RegisterAccountFragment : Fragment() {
    private var _binding: FragmentRegisterAccountBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}