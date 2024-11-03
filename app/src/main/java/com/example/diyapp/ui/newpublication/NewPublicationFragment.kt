package com.example.diyapp.ui.newpublication

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.MultipleImagesAdapterAdapter
import com.example.diyapp.databinding.FragmentNewPublicationBinding

class NewPublicationFragment : Fragment() {

    private var _binding: FragmentNewPublicationBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageUris: MutableList<Uri>
    private lateinit var recyclerViewAdapter: MultipleImagesAdapterAdapter
    private lateinit var btnImage: Button
    private lateinit var btnImages: Button
    private lateinit var ivImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPublicationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Opciones para el Spinner
        val options = listOf("Nature", "Wood", "Garden")
        val spinner: Spinner = view.findViewById(R.id.spinnerOptions)

        // Adaptador para el Spinner
        val adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        btnImage = view.findViewById(R.id.btnUploadImage)
        ivImage = view.findViewById(R.id.imageViewMainPhoto)
        btnImages = view.findViewById(R.id.btnUploadMultipleImages)

        imageUris = mutableListOf()
        recyclerViewAdapter = MultipleImagesAdapterAdapter(imageUris)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewInstructionPhotos)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

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


    }
}