package com.example.diyapp.ui.detail

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.ActivityPublicationDetailBinding

class PublicationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicationDetailBinding
    private lateinit var args: PublicationDetailActivityArgs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPublicationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPublicationInfo()

        binding.buttonAddToFavorites.setOnClickListener {
            handleAddToFavorites()
        }
    }

    private fun loadPublicationInfo() {
        args = PublicationDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedPublicationItem

        binding.apply {
            textViewTitle.text = item.title
            textViewTheme.text = item.theme
            textViewDescription.text = item.description
            textViewInstructions.text = item.instructions

            setImageFromBase64(item.photoMain, imageViewMain)

            recyclerViewInstructionPhotos.layoutManager =
                LinearLayoutManager(
                    this@PublicationDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            recyclerViewInstructionPhotos.adapter = InstructionsAdapter(item.photoProcess)
        }
    }

    private fun setImageFromBase64(base64String: String, imageView: ImageView) {
        val photoBytes = Base64.decode(base64String, Base64.DEFAULT)
        val photoBitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.size)
        imageView.setImageBitmap(photoBitmap)
    }

    private fun handleAddToFavorites() {
        if (SessionManager.isUserLoggedIn(this)) {
            SessionManager.showToast(this, R.string.addedToFavorites)
        } else {
            SessionManager.showToast(this, R.string.loginRequired)
        }
    }

}