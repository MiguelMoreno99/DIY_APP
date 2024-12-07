package com.example.diyapp.ui.mypublications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.creations.FeedCreationsAdapter
import com.example.diyapp.databinding.FragmentMyPublicationsBinding
import com.example.diyapp.ui.viewmodel.MyPublicationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MyPublicationsFragment : Fragment() {
    private var _binding: FragmentMyPublicationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FeedCreationsAdapter
    private val viewModel: MyPublicationsViewModel by viewModels()
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

        setupRecyclerView()
        setupSearchView()

        val sharedPref = SessionManager.getUserInfo(requireContext())
        email = sharedPref["email"] ?: ""

        viewModel.feedCreations.observe(viewLifecycleOwner) { creations ->
            adapter.updateData(creations)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { messageResId ->
            messageResId?.let {
                SessionManager.showToast(requireContext(), it)
                adapter.deleteData()
            }
        }

        lifecycleScope.launch {
            viewModel.loadFeedCreations(email)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.loadFeedCreations(email)
        }
    }

    private fun setupRecyclerView() {
        adapter = FeedCreationsAdapter(emptyList()) { item ->
            findNavController().navigate(
                MyPublicationsFragmentDirections.actionMyPublicationsFragmentToCreationDetailActivity(
                    item
                )
            )
        }
        binding.recyclerFeedCreations.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFeedCreations.adapter = adapter
    }

    private fun setupSearchView() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}