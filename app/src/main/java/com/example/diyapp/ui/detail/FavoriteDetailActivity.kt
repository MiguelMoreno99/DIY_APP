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
import com.example.diyapp.databinding.ActivityFavoriteDetailBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteDetailBinding
    private lateinit var args: FavoriteDetailActivityArgs
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
            removeFavoritePublication(item.idPublication)
        }
    }

    private fun removeFavoritePublication(id: Int) {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val sharedPref = SessionManager.getUserInfo(this@FavoriteDetailActivity)
                val email = sharedPref["email"]
                val idResponse = IdResponse(id, email!!)
                val call = RetrofitManager.getRetroFit().create(APIService::class.java)
                    .deleteFromFavorites(idResponse)

                val responseBody: ServerResponse = call.body()!!

                withContext(Dispatchers.Main) {

                    if (call.isSuccessful) {
                        if (responseBody.message.isNotEmpty()) {
                            SessionManager.showToast(
                                this@FavoriteDetailActivity,
                                R.string.removedFromFavorites
                            )
                            withContext(Dispatchers.Main) {
                                finish()
                            }
                        }
                    } else {
                        SessionManager.showToast(this@FavoriteDetailActivity, R.string.error2)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(this@FavoriteDetailActivity, R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }
}