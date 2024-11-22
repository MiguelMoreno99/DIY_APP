package com.example.diyapp.ui.mypublications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.data.adapter.creations.feedCreationsAdapter
import com.example.diyapp.data.adapter.creations.feedCreationsProvider
import com.example.diyapp.databinding.FragmentMyPublicationsBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("baseurl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun ShowFeedByUser(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getFeedByUser("$query/mmd.-@hotmail.com")
            val feed = call.body()
            activity?.runOnUiThread() {
                if (call.isSuccessful) {
                    feed?.User.toString()
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }
}