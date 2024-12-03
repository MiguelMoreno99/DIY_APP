package com.example.diyapp.ui.newpublication

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.MultipleImagesAdapter
import com.example.diyapp.databinding.FragmentNewPublicationBinding

class NewPublicationFragment : Fragment() {

    private var _binding: FragmentNewPublicationBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageUris: MutableList<Uri>
    private lateinit var recyclerViewAdapter: MultipleImagesAdapter
    private lateinit var btnImage: Button
    private lateinit var btnImages: Button
    private lateinit var btnPost: Button
    private lateinit var ivImage: ImageView
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etInstructions: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPublicationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val spinner: Spinner = view.findViewById(R.id.spinnerOptions)
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        btnImage = view.findViewById(R.id.btnUploadImage)
        btnPost = view.findViewById(R.id.btnPost)
        ivImage = view.findViewById(R.id.imageViewMainPhoto)
        btnImages = view.findViewById(R.id.btnUploadMultipleImages)
        etTitle = view.findViewById(R.id.etTitle)
        etDescription = view.findViewById(R.id.etDescription)
        etInstructions = view.findViewById(R.id.etInstructions)

        imageUris = mutableListOf()
        recyclerViewAdapter = MultipleImagesAdapter(imageUris)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewInstructionPhotos)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    ivImage.setImageURI(uri)
                }
            }

        val pickMedia2 =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                if (uris.isNotEmpty()) {
                    imageUris.addAll(uris)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }

        btnImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnImages.setOnClickListener {
            pickMedia2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnPost.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val title = etTitle.text.toString()
        val description = etDescription.text.toString()
        val instructions = etInstructions.text.toString()
        if (title == "" || description == "" || instructions == "" || ivImage.drawable == null || recyclerViewAdapter.itemCount == 0){
            Toast.makeText(requireContext(), "Fill all the fields First!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "Publication Created", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.exploreFragment)
        }
    }
}