package com.example.diyapp.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityFavoriteDetailBinding
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteDetailBinding
    private lateinit var args: FavoriteDetailActivityArgs
    private var useCases: UseCases = UseCases()
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPublicationInfo()
    }

    private fun loadPublicationInfo() {
        args = FavoriteDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedFavoriteItem

        val sharedPref = SessionManager.getUserInfo(this)
        email = sharedPref["email"]!!

        binding.apply {
            textViewTitle.text = item.title
            textViewTheme.text = item.theme
            textViewDescription.text = item.description
            textViewInstructions.text = item.instructions
            imageViewMain.setImageBitmap(ImageUtils.base64ToBitmap(item.photoMain))

            recyclerViewInstructionPhotos.layoutManager =
                LinearLayoutManager(
                    this@FavoriteDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            recyclerViewInstructionPhotos.adapter = InstructionsAdapter(item.photoProcess)
        }

        binding.buttonRemoveFromFavorites.setOnClickListener {
            lifecycleScope.launch {
                removeFavoritePublication(item.idPublication, email)
            }
        }
    }

    private suspend fun removeFavoritePublication(idPublication: Int, email: String) {
        val response = useCases.removeFavorite(idPublication, email)
        if (response.message.isNotEmpty()) {
            SessionManager.showToast(
                this,
                R.string.removeFavorite
            )
            withContext(Dispatchers.Main) {
                finish()
            }
        } else {
            SessionManager.showToast(
                this,
                R.string.error2
            )
        }
    }
}