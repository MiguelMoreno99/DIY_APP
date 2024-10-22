package com.example.diyapp.ui.newpublication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.diyapp.databinding.FragmentNewPublicationBinding

class NewPublicationFragment : Fragment() {

    private var _binding: FragmentNewPublicationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPublicationBinding.inflate(layoutInflater, container, true)
        return binding.root
    }
}