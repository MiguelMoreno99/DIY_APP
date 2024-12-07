package com.example.diyapp.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.explore.FeedExploreAdapter
import com.example.diyapp.databinding.FragmentExploreBinding
import com.example.diyapp.ui.viewmodel.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedExploreAdapter
    private val viewModel: ExploreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        observeViewModel()

        viewModel.loadFeed()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.loadFeed()
        }
    }

    private fun setupRecyclerView() {
        adapter = FeedExploreAdapter(emptyList()) { item ->
            findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToPublicationDetailActivity(item)
            )
        }

        binding.recyclerFeedExplore.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFeedExplore.adapter = adapter
    }

    private fun setupSearchView() {
        binding.svExplore.setOnQueryTextListener(object :
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

    private fun observeViewModel() {
        viewModel.feed.observe(viewLifecycleOwner) { feed ->
            adapter.updateData(feed)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.showNoPublicationsMessage.observe(viewLifecycleOwner) { show ->
            if (show) {
                SessionManager.showToast(requireContext(), R.string.noPublications)
                adapter.deleteData()
            }
        }
    }
}