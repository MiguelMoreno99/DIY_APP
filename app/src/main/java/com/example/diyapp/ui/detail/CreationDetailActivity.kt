package com.example.diyapp.ui.detail

import RetrofitManager
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.create.MultipleImagesAdapter
import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.data.adapter.response.IdResponse
import com.example.diyapp.data.adapter.response.ServerResponse
import com.example.diyapp.data.adapter.response.UserEditPublication
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.ActivityCreationDetailBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreationDetailBinding
    private lateinit var args: CreationDetailActivityArgs
    private val imageUris = mutableListOf<Uri>()
    private lateinit var recyclerViewAdapter: MultipleImagesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPublicationInfo()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadPublicationInfo() {

        args = CreationDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedCreationItem
        setUpCategorySpinner()
        binding.apply {
            editTextTitle.setText(item.title)
            editTextDescription.setText(item.description)
            editTextInstructions.setText(item.instructions)
            imageViewMain.setImageBitmap(ImageUtils.base64ToBitmap(item.photoMain))

            recyclerViewAdapter = MultipleImagesAdapter(imageUris)
            binding.recyclerViewInstructionPhotos.apply {
                adapter = recyclerViewAdapter
                layoutManager =
                    LinearLayoutManager(this@CreationDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            }
            recyclerViewInstructionPhotos.adapter = InstructionsAdapter(item.photoProcess)

            setUpImagePickers()

            buttonEditPublication.setOnClickListener { validateFields(item) }
            buttonDeletePublication.setOnClickListener { deletePublication(item.idPublication) }
        }
    }

    private fun setUpCategorySpinner() {
        val options = resources.getStringArray(R.array.category_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOptionsTheme.adapter = adapter

        val item = args.feedCreationItem
        setSpinnerSelection(binding.spinnerOptionsTheme, item.theme)
    }

    private fun setSpinnerSelection(spinner: Spinner, theme: String) {
        val adapter = spinner.adapter

        for (i in 0 until adapter.count) {
            val item = adapter.getItem(i).toString()
            if (item == theme) {
                spinner.setSelection(i)
                break
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpImagePickers() {
        val pickMainImage =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let { binding.imageViewMain.setImageURI(it) }
            }

        val pickInstructionImages =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                if (uris.isNotEmpty()) {
                    val startIndex = binding.recyclerViewInstructionPhotos.adapter!!.itemCount
                    imageUris.clear()
                    binding.recyclerViewInstructionPhotos.adapter!!.notifyItemRangeRemoved(0,startIndex)
                    imageUris.addAll(uris)
                    binding.recyclerViewInstructionPhotos.adapter = MultipleImagesAdapter(imageUris)
                }
            }

        binding.buttonUpdateMainImage.setOnClickListener {
            pickMainImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.buttonUpdateInstructionPhotos.setOnClickListener {
            pickInstructionImages.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun validateFields(item: FeedCreations) {
        val title = binding.editTextTitle.text.toString()
        val category = binding.spinnerOptionsTheme.selectedItem.toString()
        val description = binding.editTextDescription.text.toString()
        val instructions = binding.editTextInstructions.text.toString()
        val mainPhoto = ImageUtils.bitmapToBase64(binding.imageViewMain.drawToBitmap())
        val photos = recyclerViewAdapter.getImagesAsBase64(this)

        when {
            title.isBlank() || description.isBlank() || instructions.isBlank() -> {
                SessionManager.showToast(this, R.string.fillFields)
            }

            else -> {
                editPublication(
                    item.idPublication,
                    title,
                    category,
                    mainPhoto,
                    description,
                    instructions,
                    photos
                )
            }
        }
    }

    private fun deletePublication(id: Int) {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val sharedPref = SessionManager.getUserInfo(this@CreationDetailActivity)
                val email = sharedPref["email"]
                val idResponse = IdResponse(id, email!!)
                val call = RetrofitManager.getRetroFit().create(APIService::class.java)
                    .deleteCreation(idResponse)

                val responseBody: ServerResponse = call.body()!!

                withContext(Dispatchers.Main) {

                    if (call.isSuccessful) {
                        if (responseBody.message.isNotEmpty()) {
                            SessionManager.showToast(
                                this@CreationDetailActivity,
                                R.string.publicationDeleted
                            )
                            withContext(Dispatchers.Main) {
                                finish()
                            }
                        }
                    } else {
                        SessionManager.showToast(this@CreationDetailActivity, R.string.error2)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(this@CreationDetailActivity, R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }

    private fun editPublication(
        id: Int,
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ) {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val sharedPref = SessionManager.getUserInfo(this@CreationDetailActivity)
                val email = sharedPref["email"]
                val creation = UserEditPublication(
                    id,
                    email!!,
                    title,
                    theme,
                    photoMain,
                    description,
                    instructions,
                    photoProcess
                )
                val call = RetrofitManager.getRetroFit().create(APIService::class.java)
                    .editCreation(creation)

                val responseBody: ServerResponse = call.body()!!

                withContext(Dispatchers.Main) {

                    if (call.isSuccessful) {
                        if (responseBody.message.isNotEmpty()) {
                            SessionManager.showToast(
                                this@CreationDetailActivity,
                                R.string.publicationEdited
                            )
                            withContext(Dispatchers.Main) {
                                finish()
                            }
                        }
                    } else {
                        SessionManager.showToast(this@CreationDetailActivity, R.string.error2)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(this@CreationDetailActivity, R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }

}