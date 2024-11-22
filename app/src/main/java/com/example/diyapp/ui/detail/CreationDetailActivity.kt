package com.example.diyapp.ui.detail

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.MultipleImagesAdapterAdapter
import com.example.diyapp.data.adapter.explore.InstructionsAdapter
import com.example.diyapp.databinding.ActivityCreationDetailBinding

class CreationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreationDetailBinding
    private lateinit var etTitle: EditText
    private lateinit var spCategory: Spinner
    private lateinit var ivMainPhoto: ImageView
    private lateinit var rvInstructionsPhotos: RecyclerView
    private lateinit var etDescription: EditText
    private lateinit var etInstructions: EditText
    private lateinit var args: CreationDetailActivityArgs
    private lateinit var imageUris: MutableList<Uri>
    private lateinit var recyclerViewAdapter: MultipleImagesAdapterAdapter
    private lateinit var btnImage: Button
    private lateinit var btnImages: Button
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPublicationInfo()
    }

    private fun loadPublicationInfo() {
        etTitle = findViewById(R.id.editTextTitle)
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
        spCategory = findViewById(R.id.spinnerOptionsTheme)
        etDescription = findViewById(R.id.editTextDescription)
        etInstructions = findViewById(R.id.editTextInstructions)
        ivMainPhoto = findViewById(R.id.imageViewMain)
        btnImage = findViewById(R.id.buttonUpdateMainImage)
        rvInstructionsPhotos = findViewById(R.id.recyclerViewInstructionPhotos)
        btnImages = findViewById(R.id.buttonUpdateInstructionPhotos)
        btnEdit = findViewById(R.id.buttonEditPublication)
        btnDelete = findViewById(R.id.buttonDeletePublication)
        args = CreationDetailActivityArgs.fromBundle(intent.extras!!)
        val item = args.feedCreationItem
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = adapter

        etTitle.setText(item.title)
        setSpinnerSelection(spCategory, item.Theme)

        val photoMainBytes = Base64.decode(item.photoMain, Base64.DEFAULT)
        val photoMainBitmap = BitmapFactory.decodeByteArray(photoMainBytes, 0, photoMainBytes.size)
        ivMainPhoto.setImageBitmap(photoMainBitmap)
        etDescription.setText(item.description)
        etInstructions.setText(item.instructions)
        rvInstructionsPhotos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvInstructionsPhotos.adapter = InstructionsAdapter(item.photoProcess)

        imageUris = mutableListOf()
        recyclerViewAdapter = MultipleImagesAdapterAdapter(imageUris)

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    ivMainPhoto.setImageURI(uri)
                }
            }

        val pickMedia2 =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                if (uris.isNotEmpty()) {
                    imageUris.addAll(uris)
                    rvInstructionsPhotos.adapter = recyclerViewAdapter
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }

        btnImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnImages.setOnClickListener {
            pickMedia2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnEdit.setOnClickListener {
            validateFields()
        }

        btnDelete.setOnClickListener {
            Toast.makeText(this, "Publication Deleted", Toast.LENGTH_SHORT).show()
            finish() // asegúrate de que se llame después de que se complete la tarea asíncrona.
        }
    }

    private fun setSpinnerSelection(spinner: Spinner, theme: String) {
        val adapter = spCategory.adapter

        for (i in 0 until adapter.count) {
            val item = adapter.getItem(i).toString()
            if (item == theme) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun validateFields() {
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()
        val instructions = etInstructions.text.toString()
        if (title == "" || description == "" || instructions == ""){
            Toast.makeText(this, "Fill all the fields First!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Publication Edited!", Toast.LENGTH_SHORT).show()
            finish() // asegúrate de que se llame después de que se complete la tarea asíncrona.
        }
    }
}