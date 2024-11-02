package com.example.diyapp.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.diyapp.data.adapter.explore.feedExplore
import com.example.diyapp.databinding.ActivityPublicationDetailBinding

class PublicationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicationDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val feedExploreItem = intent?.getParcelableExtra<feedExplore>("feedExploreItem")
        if (feedExploreItem != null) {

        }

        binding = ActivityPublicationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}