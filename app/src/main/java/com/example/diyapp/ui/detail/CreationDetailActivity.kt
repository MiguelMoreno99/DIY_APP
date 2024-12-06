package com.example.diyapp.ui.detail

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.create.MultipleImagesAdapter
import com.example.diyapp.data.adapter.creations.FeedCreations
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityCreationDetailBinding
import com.example.diyapp.ui.viewmodel.CreationDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreationDetailBinding
    private lateinit var args: CreationDetailActivityArgs
    private val viewModel: CreationDetailViewModel by viewModels()
    private lateinit var email: String
    private val imageUris = mutableListOf<Uri>()
    private lateinit var recyclerViewAdapter: MultipleImagesAdapter

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPublicationInfo()
        setupObservers()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadPublicationInfo() {
        args = CreationDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedCreationItem

        email = sessionManager.getUserInfo(this)["email"]!!

        setUpCategorySpinner(item.theme)
        binding.apply {
            editTextTitle.setText(item.title)
            editTextDescription.setText(item.description)
            editTextInstructions.setText(item.instructions)
            imageViewMain.setImageBitmap(ImageUtils.base64ToBitmap(item.photoMain))

            recyclerViewAdapter = MultipleImagesAdapter(imageUris)
            recyclerViewInstructionPhotos.apply {
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(
                    this@CreationDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

            recyclerViewInstructionPhotos.adapter = InstructionsAdapter(item.photoProcess)

            setUpImagePickers()
            buttonEditPublication.setOnClickListener { validateFields(item) }
            buttonDeletePublication.setOnClickListener {
                viewModel.deletePublication(item.idPublication, email)
            }
        }
    }

    private fun setUpCategorySpinner(theme: String) {
        val options = resources.getStringArray(R.array.category_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOptionsTheme.adapter = adapter
        setSpinnerSelection(binding.spinnerOptionsTheme, theme)
    }

    private fun setSpinnerSelection(spinner: Spinner, theme: String) {
        val adapter = spinner.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i).toString() == theme) {
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
                    binding.recyclerViewInstructionPhotos.adapter!!.notifyItemRangeRemoved(
                        0,
                        startIndex
                    )
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

        if (title.isBlank() || description.isBlank() || instructions.isBlank()) {
            sessionManager.showToast(this, R.string.fillFields)
        } else {
            viewModel.editPublication(
                item.idPublication,
                email,
                title,
                category,
                mainPhoto,
                description,
                instructions,
                photos
            )
        }
    }

    private fun setupObservers() {
        viewModel.operationResult.observe(this) { result ->
            when (result) {
                "PublicationDeleted" -> {
                    sessionManager.showToast(this, R.string.publicationDeleted)
                    finish()
                }

                "PublicationEdited" -> {
                    sessionManager.showToast(this, R.string.publicationEdited)
                    finish()
                }

                "Error" -> sessionManager.showToast(this, R.string.error2)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}