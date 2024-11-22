package com.example.diyapp.ui.detail

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityFavoriteDetailBinding

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteDetailBinding
    private lateinit var tvTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var ivMainPhoto: ImageView
    private lateinit var rvInstructionsPhotos: RecyclerView
    private lateinit var tvDescription: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var btnRemove: Button
    private lateinit var args: FavoriteDetailActivityArgs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvTitle = findViewById(R.id.textViewTitle)
        tvCategory = findViewById(R.id.textViewTheme)
        tvDescription = findViewById(R.id.textViewDescription)
        tvInstructions = findViewById(R.id.textViewInstructions)
        ivMainPhoto = findViewById(R.id.imageViewMain)
        btnRemove = findViewById(R.id.buttonRemoveFromFavorites)
        rvInstructionsPhotos = findViewById(R.id.recyclerViewInstructionPhotos)

        loadPublicationInfo()

        btnRemove.setOnClickListener {
            Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show()
            finish() // asegúrate de que se llame después de que se complete la tarea asíncrona.
        }
    }

    private fun loadPublicationInfo() {
        args = FavoriteDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedFavoriteItem
        tvTitle.text = item.title
        tvCategory.text = item.Theme

        val photoMainBytes = Base64.decode(item.photoMain, Base64.DEFAULT)
        val photoMainBitmap = BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
        ivMainPhoto.setImageBitmap(photoMainBitmap)
        tvDescription.text = item.description
        tvInstructions.text = item.instructions
        rvInstructionsPhotos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvInstructionsPhotos.adapter = InstructionsAdapter(item.photoProcess)
    }
}