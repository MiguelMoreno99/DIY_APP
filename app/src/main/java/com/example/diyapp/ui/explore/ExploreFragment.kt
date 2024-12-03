package com.example.diyapp.ui.explore

import RetrofitManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.data.adapter.explore.FeedExploreAdapter
import com.example.diyapp.data.adapter.explore.FeedExploreProvider
import com.example.diyapp.databinding.FragmentExploreBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedExploreAdapter
    private val feedExploreList = FeedExploreProvider.feedExploreList

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
        showFeed()
    }

    private fun showFeed() {

        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call =
                    RetrofitManager.getRetrofit().create(APIService::class.java).getFeedExplore()

                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")

                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE

                    if (call.isSuccessful && responseBody != null) {
                        if (responseBody.isNotEmpty()) {
                            adapter.updateData(responseBody)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "There are no publications",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Unable to load data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Exception: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = FeedExploreAdapter(feedExploreList) { item ->
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