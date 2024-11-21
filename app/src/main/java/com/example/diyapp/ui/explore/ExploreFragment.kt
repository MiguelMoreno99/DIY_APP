package com.example.diyapp.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.data.adapter.explore.feedExploreAdapter
import com.example.diyapp.data.adapter.explore.feedExploreProvider
import com.example.diyapp.databinding.FragmentExploreBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: feedExploreAdapter

    //private val feed = List<feedExplore>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter =
            feedExploreAdapter(feedExploreProvider.feedExploreList) { item ->
                findNavController().navigate(
                    ExploreFragmentDirections.actionExploreFragmentToPublicationDetailActivity(
                        item
                    )
                )
            }
        binding.recyclerFeedExplore.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFeedExplore.adapter = adapter

        // Configurar el SearchView
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

//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerFeedExplore)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        recyclerView.adapter = feedExploreAdapter(feedExploreProvider.feedExploreList) { item ->
//            findNavController().navigate(
//                ExploreFragmentDirections.actionExploreFragmentToPublicationDetailActivity(
//                    item
//                )
//            )
//        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("url/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByTheme(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getFeedByTheme("$query/images")
            val feed = call.body()
            activity?.runOnUiThread() {
                if (call.isSuccessful) {
                    val publications = feed
                    feedExploreProvider.feedExploreList

                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Error ", Toast.LENGTH_SHORT).show()
    }
}