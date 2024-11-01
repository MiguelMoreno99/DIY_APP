package com.example.diyapp.ui.newpublication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.favorites.feedFavoritesAdapter
import com.example.diyapp.data.adapter.favorites.feedFavoritesProvider
import com.example.diyapp.databinding.FragmentNewPublicationBinding

class NewPublicationFragment : Fragment() {

    private var _binding: FragmentNewPublicationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPublicationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Opciones para el Spinner
        val options = listOf("Nature", "Wood", "Garden")
        val spinner: Spinner = view.findViewById(R.id.spinnerOptions)

        // Adaptador para el Spinner
        val adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }
}