package com.example.diyapp.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityFavoriteDetailBinding
import com.example.diyapp.ui.viewmodel.FavoriteDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteDetailBinding
    private val viewModel: FavoriteDetailViewModel by viewModels()
    private lateinit var args: FavoriteDetailActivityArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        args = FavoriteDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedFavoriteItem

        setupObservers()
        viewModel.loadPublicationDetails(item)

        binding.buttonRemoveFromFavorites.setOnClickListener {
            viewModel.removeFavorite(item.idPublication, getEmailFromSession())
        }
    }

    private fun setupObservers() {
        viewModel.title.observe(this) { binding.textViewTitle.text = it }
        viewModel.theme.observe(this) { binding.textViewTheme.text = it }
        viewModel.description.observe(this) { binding.textViewDescription.text = it }
        viewModel.instructions.observe(this) { binding.textViewInstructions.text = it }
        viewModel.mainPhoto.observe(this) {
            binding.imageViewMain.setImageBitmap(ImageUtils.base64ToBitmap(it))
        }
        viewModel.photoProcess.observe(this) {
            binding.recyclerViewInstructionPhotos.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false
            )
            binding.recyclerViewInstructionPhotos.adapter = InstructionsAdapter(it)
        }
        viewModel.isFavoriteRemoved.observe(this) { isRemoved ->
            if (isRemoved) {
                SessionManager.showToast(this, R.string.removeFavorite)
                finish()
            } else {
                SessionManager.showToast(this, R.string.error2)
            }
        }
    }

    private fun getEmailFromSession(): String {
        val sharedPref = SessionManager.getUserInfo(this)
        return sharedPref["email"] ?: ""
    }
}