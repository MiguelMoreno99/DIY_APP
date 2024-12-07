package com.example.diyapp.ui.favorites

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
import com.example.diyapp.data.adapter.favorites.FeedFavoritesAdapter
import com.example.diyapp.databinding.FragmentFavoritesBinding
import com.example.diyapp.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedFavoritesAdapter
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = SessionManager.getUserInfo(requireContext())["email"]!!

        setupRecyclerView()
        setupObservers()
        setupSearchView()

        viewModel.loadFavorites(email)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.loadFavorites(email)
        }
    }

    private fun setupRecyclerView() {
        adapter = FeedFavoritesAdapter(emptyList()) { item ->
            findNavController().navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToFavoriteDetailActivity(item)
            )
        }

        binding.recyclerFeedFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFeedFavorites.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter.updateData(favorites)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.emptyState.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                SessionManager.showToast(requireContext(), R.string.notHaveFavorites)
                adapter.deleteData()
            }
        }
    }

    private fun setupSearchView() {
        binding.svFavorites.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.filterFavorites(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.filterFavorites(it) }
                return true
            }
        })
    }
}