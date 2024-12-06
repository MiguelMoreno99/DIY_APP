package com.example.diyapp.ui.mypublications

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
import com.example.diyapp.data.adapter.creations.FeedCreationsAdapter
import com.example.diyapp.data.adapter.creations.FeedCreationsProvider
import com.example.diyapp.databinding.FragmentMyPublicationsBinding
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyPublicationsFragment : Fragment() {
    private var _binding: FragmentMyPublicationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedCreationsAdapter
    private var useCases: UseCases = UseCases()
    private var email: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPublicationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = SessionManager.getUserInfo(requireContext())
        email = sharedPref["email"]!!

        adapter =
            FeedCreationsAdapter(FeedCreationsProvider.feedCreationsList) { item ->
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
        val response = useCases.getFeedCreations(email)
        if (response.isNotEmpty()) {
            adapter.updateData(response)
        } else {
            SessionManager.showToast(requireContext(), R.string.notHavePublications)
            adapter.deleteData()
        }
    }
}