package com.example.diyapp.ui.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.MultipleImagesAdapterAdapter
import com.example.diyapp.databinding.ActivityCreationDetailBinding

class CreationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreationDetailBinding
    private lateinit var tvTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var ivMainPhoto: ImageView
    private lateinit var rvInstructionsPhotos: RecyclerView
    private lateinit var recyclerViewAdapter: MultipleImagesAdapterAdapter
    private lateinit var tvDescription: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var args: CreationDetailActivityArgs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPublicationInfo()
    }

    private fun loadPublicationInfo() {
        tvTitle = findViewById(R.id.textViewTitle)
        tvCategory = findViewById(R.id.textViewTheme)
        tvDescription = findViewById(R.id.textViewDescription)
        tvInstructions = findViewById(R.id.textViewInstructions)

        args = CreationDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedCreationItem
        tvTitle.text = item.title
        tvCategory.text = item.Theme
        tvDescription.text = item.description
        tvInstructions.text = item.instructions
    }
}