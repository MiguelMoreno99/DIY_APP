package com.example.diyapp.ui.detail

import RetrofitManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.data.adapter.response.IdResponse
import com.example.diyapp.data.adapter.response.ServerResponse
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.ActivityPublicationDetailBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PublicationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicationDetailBinding
    private lateinit var args: PublicationDetailActivityArgs
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
            addFavoritePublication(idPublication)
        } else {
            SessionManager.showToast(this, R.string.loginRequired)
        }
    }

    private fun addFavoritePublication(id: Int) {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val sharedPref = SessionManager.getUserInfo(this@PublicationDetailActivity)
                val email = sharedPref["email"]
                val idResponse = IdResponse(id, email!!)
                val call = RetrofitManager.getRetroFit().create(APIService::class.java)
                    .addToFavorites(idResponse)

                val responseBody: ServerResponse = call.body()!!

                withContext(Dispatchers.Main) {

                    if (call.isSuccessful) {
                        if (responseBody.message.isNotEmpty()) {
                            SessionManager.showToast(
                                this@PublicationDetailActivity,
                                R.string.addedToFavorites
                            )
                            withContext(Dispatchers.Main) {
                                finish()
                            }
                        }
                    } else {
                        SessionManager.showToast(this@PublicationDetailActivity, R.string.error2)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(this@PublicationDetailActivity, R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }

}