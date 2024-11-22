package com.example.diyapp.ui.detail

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityPublicationDetailBinding
import com.example.diyapp.ui.explore.MainActivity

class PublicationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicationDetailBinding
    private lateinit var tvTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var ivMainPhoto: ImageView
    private lateinit var rvInstructionsPhotos: RecyclerView
    private lateinit var tvDescription: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var btnAddFavorite: Button
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
        btnAddFavorite = findViewById(R.id.buttonAddToFavorites)

        args = PublicationDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedPublicationItem
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

        btnAddFavorite.setOnClickListener{
            if (isUserLoggedIn()){
                Toast.makeText(this, "Added to Favorites!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "You need to Login First!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isUserLoggedIn(): Boolean {
        val sharedPref = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isLoggedIn", false)
    }
}