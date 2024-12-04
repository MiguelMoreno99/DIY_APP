package com.example.diyapp.ui.detail

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.MultipleImagesAdapter
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.ActivityCreationDetailBinding

class CreationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreationDetailBinding
    private lateinit var args: CreationDetailActivityArgs
    private lateinit var imageUris: MutableList<Uri>
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
            imageUris = mutableListOf()

            setImageFromBase64(item.photoMain, imageViewMain)

            recyclerViewAdapter = MultipleImagesAdapter(imageUris)
            recyclerViewInstructionPhotos.layoutManager = LinearLayoutManager(
                this@CreationDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            recyclerViewInstructionPhotos.adapter = recyclerViewAdapter

            setUpImagePickers()

            buttonEditPublication.setOnClickListener { validateFields() }
            buttonDeletePublication.setOnClickListener { deletePublication() }
        }
    }

    private fun setUpCategorySpinner() {
        val options = listOf(
            "Reciclaje",
            "Pintura",
            "Decoración",
            "Carpintería",
            "Costura",
            "Textil",
            "Joyería",
            "Papelería",
            "Cerámica",
            "Modelado",
            "Jardinería",
            "Organización",
            "Regalos",
            "Juguetes",
            "Muebles"
        )
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
                    imageUris.addAll(uris)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }

        binding.buttonUpdateMainImage.setOnClickListener {
            pickMainImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.buttonUpdateInstructionPhotos.setOnClickListener {
            pickInstructionImages.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun validateFields() {
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextDescription.text.toString()
        val instructions = binding.editTextInstructions.text.toString()

        when {
            title.isBlank() || description.isBlank() || instructions.isBlank() -> {
                SessionManager.showToast(this, R.string.fillFields)
            }

            else -> {
                SessionManager.showToast(this, R.string.publicationEdited)
                finish() // asegúrate de que se llame después de que se complete la tarea asíncrona.
            }
        }
    }

    private fun deletePublication() {
        // Aquí va tu lógica de eliminación de la publicación
        SessionManager.showToast(this, R.string.publicationDeleted)
    }

    private fun setImageFromBase64(base64String: String, imageView: ImageView) {
        val photoBytes = Base64.decode(base64String, Base64.DEFAULT)
        val photoBitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.size)
        imageView.setImageBitmap(photoBitmap)
    }

}