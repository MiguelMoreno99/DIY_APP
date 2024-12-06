package com.example.diyapp.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.explore.FeedExploreAdapter
import com.example.diyapp.data.adapter.explore.FeedExploreProvider
import com.example.diyapp.databinding.FragmentExploreBinding
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedExploreAdapter
    private var useCases: UseCases = UseCases()

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
    }

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            delay(1000)
            showFeed()
            binding.progressBar.visibility = View.GONE
        }
    }

    private suspend fun showFeed() {
        val response = useCases.getFeedExplore()
        if (response.isNotEmpty()) {
            adapter.updateData(response)
        } else {
            SessionManager.showToast(requireContext(), R.string.noPublications)
            adapter.deleteData()
        }
    }

    private fun setupRecyclerView() {
        adapter = FeedExploreAdapter(FeedExploreProvider.feedExploreList) { item ->
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
}