package com.example.diyapp.ui.detail

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPublicationInfo()
    }

    private fun loadPublicationInfo() {
        etTitle = findViewById(R.id.editTextTitle)
        val options = listOf("Reciclaje", "Pintura", "Decoración", "Carpintería", "Costura", "Textil", "Joyería", "Papelería", "Cerámica", "Modelado", "Jardinería", "Organización", "Regalos", "Juguetes", "Muebles")
        spCategory = findViewById(R.id.spinnerOptionsTheme)
        etDescription = findViewById(R.id.editTextDescription)
        etInstructions = findViewById(R.id.editTextInstructions)
        ivMainPhoto = findViewById(R.id.imageViewMain)
        rvInstructionsPhotos = findViewById(R.id.recyclerViewInstructionPhotos)
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
}