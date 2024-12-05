package com.example.diyapp.ui.favorites

import RetrofitManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.adapter.favorites.FeedFavoritesAdapter
import com.example.diyapp.data.adapter.favorites.FeedFavoritesProvider
import com.example.diyapp.data.adapter.response.UserEmail
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.FragmentFavoritesBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedFavoritesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        lifecycleScope.launch {
            delay(1000)
            showFeed()
        }
    }

    private fun showFeed() {

        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val sharedPref = SessionManager.getUserInfo(requireContext())
                val email = sharedPref["email"]
                val user = UserEmail(email!!)
                val call = RetrofitManager.getRetroFit().create(APIService::class.java)
                    .getFeedFavorites(user)

                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")

                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE

                    if (call.isSuccessful && responseBody != null) {
                        if (responseBody.isNotEmpty()) {
                            adapter.updateData(responseBody)
                        } else {
                            SessionManager.showToast(requireContext(), R.string.notHaveFavorites)
                            adapter.deleteData()
                        }
                    } else {
                        SessionManager.showToast(requireContext(), R.string.unableToLoadData)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(requireContext(), R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }
}