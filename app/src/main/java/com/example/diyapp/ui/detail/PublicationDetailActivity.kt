package com.example.diyapp.ui.detail

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.MultipleImagesAdapterAdapter
import com.example.diyapp.data.adapter.explore.ImagesFeedExploreAdapter
import com.example.diyapp.databinding.ActivityPublicationDetailBinding

class PublicationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicationDetailBinding
    private lateinit var tvTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var ivMainPhoto: ImageView
    private lateinit var rvInstructionsPhotos: RecyclerView
    private lateinit var recyclerViewAdapter: ImagesFeedExploreAdapter
    private lateinit var tvDescription: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var args: PublicationDetailActivityArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPublicationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPublicationInfo()
    }

    private fun loadPublicationInfo() {
        tvTitle = findViewById(R.id.textViewTitle)
        tvCategory = findViewById(R.id.textViewTheme)
        tvDescription = findViewById(R.id.textViewDescription)
        tvInstructions = findViewById(R.id.textViewInstructions)
        ivMainPhoto = findViewById(R.id.imageViewMain)
        rvInstructionsPhotos = findViewById(R.id.recyclerViewInstructionPhotos)

        args = PublicationDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedPublicationItem
        tvTitle.text = item.title
        tvCategory.text = item.Theme

        // Decodificar y mostrar la portada
        val photoMainBytes = Base64.decode(item.photoMain, Base64.DEFAULT)
        val photoMainBitmap = BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
        ivMainPhoto.setImageBitmap(photoMainBitmap)
        tvDescription.text = item.description
        tvInstructions.text = item.instructions
        //rvInstructionsPhotos.adapter = ImagesFeedExploreAdapter(item.photoProcess)
        //rvInstructionsPhotos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}