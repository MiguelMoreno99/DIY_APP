package com.example.diyapp.ui.mypublications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.data.adapter.creations.feedCreationsAdapter
import com.example.diyapp.data.adapter.creations.feedCreationsProvider
import com.example.diyapp.databinding.FragmentMyPublicationsBinding

class MyPublicationsFragment : Fragment() {
    private var _binding: FragmentMyPublicationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: feedCreationsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPublicationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter =
            feedCreationsAdapter(feedCreationsProvider.feedCreationsList) { item ->
                findNavController().navigate(
                    MyPublicationsFragmentDirections.actionMyPublicationsFragmentToCreationDetailActivity(
                        item
                    )
                )
            }
        binding.recyclerFeedCreations.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFeedCreations.adapter = adapter

        binding.svCreations.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { adapter.filter(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.filter(it) }
                return true
            }
        })
    }
}