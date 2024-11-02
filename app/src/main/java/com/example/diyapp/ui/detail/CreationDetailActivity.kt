package com.example.diyapp.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.diyapp.data.adapter.creations.feedCreations
import com.example.diyapp.databinding.ActivityCreationDetailBinding

class CreationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreationDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val feedCreationItem = intent?.getParcelableExtra<feedCreations>("feedCreationItem")
        if (feedCreationItem != null) {

        }

        binding = ActivityCreationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}