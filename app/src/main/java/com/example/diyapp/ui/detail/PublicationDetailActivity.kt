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
import com.example.diyapp.databinding.ActivityPublicationDetailBinding
import com.example.diyapp.domain.UseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PublicationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicationDetailBinding
    private lateinit var args: PublicationDetailActivityArgs
    private var useCases: UseCases = UseCases()
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPublicationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPublicationInfo()
    }

    private fun loadPublicationInfo() {
        args = PublicationDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedPublicationItem

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
                    this@PublicationDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            recyclerViewInstructionPhotos.adapter = InstructionsAdapter(item.photoProcess)
        }

        binding.buttonAddToFavorites.setOnClickListener {
            handleAddToFavorites(item.idPublication)
        }

    }

    private fun handleAddToFavorites(idPublication: Int) {
        if (SessionManager.isUserLoggedIn(this)) {
            lifecycleScope.launch {
                addFavoritePublication(idPublication, email)
            }
        } else {
            SessionManager.showToast(this, R.string.loginRequired)
        }
    }

    private suspend fun addFavoritePublication(idPublication: Int, email: String) {
        val response = useCases.addFavoritePublication(idPublication, email)
        if (response.message.isNotEmpty()) {
            SessionManager.showToast(
                this,
                R.string.addedToFavorites
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