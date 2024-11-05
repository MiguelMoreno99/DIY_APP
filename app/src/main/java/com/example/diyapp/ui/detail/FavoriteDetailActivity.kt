package com.example.diyapp.ui.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.MultipleImagesAdapterAdapter
import com.example.diyapp.databinding.ActivityFavoriteDetailBinding

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteDetailBinding
    private lateinit var tvTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var ivMainPhoto: ImageView
    private lateinit var rvInstructionsPhotos: RecyclerView
    private lateinit var recyclerViewAdapter: MultipleImagesAdapterAdapter
    private lateinit var tvDescription: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var args: FavoriteDetailActivityArgs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPublicationInfo()
    }

    private fun loadPublicationInfo() {
        tvTitle = findViewById(R.id.textViewTitle)
        tvCategory = findViewById(R.id.textViewTheme)
        tvDescription = findViewById(R.id.textViewDescription)
        tvInstructions = findViewById(R.id.textViewInstructions)

        args = FavoriteDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedFavoriteItem
        tvTitle.text = item.title
        tvCategory.text = item.Theme
        tvDescription.text = item.description
        tvInstructions.text = item.instructions
    }
}