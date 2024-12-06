package com.example.diyapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityPublicationDetailBinding
import com.example.diyapp.ui.viewmodel.PublicationDetailViewModel

class PublicationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicationDetailBinding
    private lateinit var args: PublicationDetailActivityArgs
    private val viewModel: PublicationDetailViewModel by viewModels()
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        args = PublicationDetailActivityArgs.fromBundle(intent.extras!!)
        email = SessionManager.getUserInfo(this)["email"]!!
        viewModel.loadPublicationInfo(args.feedPublicationItem)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.publication.observe(this) { item ->
            binding.apply {
                textViewTitle.text = item.title
                textViewTheme.text = item.theme
                textViewDescription.text = item.description
                textViewInstructions.text = item.instructions
                imageViewMain.setImageBitmap(ImageUtils.base64ToBitmap(item.photoMain))

                recyclerViewInstructionPhotos.layoutManager = LinearLayoutManager(
                    this@PublicationDetailActivity, LinearLayoutManager.HORIZONTAL, false
                )
                recyclerViewInstructionPhotos.adapter = InstructionsAdapter(item.photoProcess)
            }
        }

        viewModel.isAddedToFavorites.observe(this) { isAdded ->
            if (isAdded) {
                SessionManager.showToast(this, R.string.addedToFavorites)
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                SessionManager.showToast(this, it)
            }
        }
    }

    private fun setupListeners() {
        binding.buttonAddToFavorites.setOnClickListener {
            val item = viewModel.publication.value
            if (SessionManager.isUserLoggedIn(this) && item != null) {
                viewModel.addFavoritePublication(item.idPublication, email)
            } else {
                SessionManager.showToast(this, R.string.loginRequired)
            }
        }
    }
}
