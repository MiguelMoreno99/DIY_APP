package com.example.diyapp.ui.mypublications

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.data.adapter.creations.FeedCreationsAdapter
import com.example.diyapp.data.adapter.creations.feedCreationsProvider
import com.example.diyapp.data.adapter.response.UserEmail
import com.example.diyapp.databinding.FragmentMyPublicationsBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            FeedCreationsAdapter(feedCreationsProvider.feedCreationsList) { item ->
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

    private fun getRetrofit(): Retrofit {
        // Configuración del interceptor de logging
        val logging = HttpLoggingInterceptor()
        logging.level =
            HttpLoggingInterceptor.Level.BODY

        // Configuración del OkHttpClient con el interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Crea y retorna el objeto Retrofit con el cliente configurado
        return Retrofit.Builder()
            .baseUrl("http://myprojectapi.com.preview.services/") // http://192.168.100.18/
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun showFeed() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sharedPref =
                    requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                val mail = sharedPref.getString("email", "")!!
                val user = UserEmail(mail)
                val call = getRetrofit().create(APIService::class.java).getFeedCreations(user)

                val responseBody = call.body()
                Log.d("API Response", "Server Response: $responseBody")

                val feed = call.body()

                withContext(Dispatchers.Main) {
                    if (call.isSuccessful && feed != null) {
                        if (feed.isNotEmpty()){
                            adapter.updateData(feed)
                        }else{
                            Toast.makeText(requireContext(), "You not have publications", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Unable to load data", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Excepción: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }
}