package com.example.diyapp.ui.mypublications

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
import com.example.diyapp.data.adapter.creations.FeedCreationsAdapter
import com.example.diyapp.data.adapter.creations.FeedCreationsProvider
import com.example.diyapp.data.adapter.response.UserEmail
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.FragmentMyPublicationsBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPublicationsFragment : Fragment() {
    private var _binding: FragmentMyPublicationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FeedCreationsAdapter
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
        showFeed()
    }

    override fun onResume() {
        super.onResume()
        showFeed()
    }

    private fun showFeed() {

        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val sharedPref = SessionManager.getUserInfo(requireContext())
                val email = sharedPref["email"]
                val user = UserEmail(email!!)
                val call = RetrofitManager.getRetroFit().create(APIService::class.java)
                    .getFeedCreations(user)

                val responseBody = call.body()

                withContext(Dispatchers.Main) {

                    binding.progressBar.visibility = View.GONE

                    if (call.isSuccessful && responseBody != null) {
                        if (responseBody.isNotEmpty()) {
                            adapter.updateData(responseBody)
                        } else {
                            SessionManager.showToast(requireContext(), R.string.notHavePublications)
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