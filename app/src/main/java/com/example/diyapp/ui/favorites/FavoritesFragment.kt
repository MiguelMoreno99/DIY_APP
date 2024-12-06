package com.example.diyapp.ui.favorites

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
import com.example.diyapp.data.adapter.favorites.FeedFavoritesAdapter
import com.example.diyapp.data.adapter.favorites.FeedFavoritesProvider
import com.example.diyapp.databinding.FragmentFavoritesBinding
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedFavoritesAdapter
    private var useCases: UseCases = UseCases()
    private var email: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = SessionManager.getUserInfo(requireContext())
        email = sharedPref["email"]!!

        adapter =
            FeedFavoritesAdapter(FeedFavoritesProvider.feedFavoritesList) { item ->
                findNavController().navigate(
                    FavoritesFragmentDirections.actionFavoritesFragmentToFavoriteDetailActivity(
                        item
                    )
                )
            }
        binding.recyclerFeedFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFeedFavorites.adapter = adapter

        binding.svFavorites.setOnQueryTextListener(object :
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

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            delay(1000)
            showFeed(email)
            binding.progressBar.visibility = View.GONE
        }
    }

    private suspend fun showFeed(email: String) {
        val response = useCases.getFeedFavorite(email)
        if (response.isNotEmpty()) {
            adapter.updateData(response)
        } else {
            SessionManager.showToast(requireContext(), R.string.notHaveFavorites)
            adapter.deleteData()
        }
    }
}