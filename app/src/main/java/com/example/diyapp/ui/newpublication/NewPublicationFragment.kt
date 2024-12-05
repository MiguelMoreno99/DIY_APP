package com.example.diyapp.ui.newpublication

import RetrofitManager
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.create.MultipleImagesAdapter
import com.example.diyapp.data.adapter.response.ServerResponse
import com.example.diyapp.data.adapter.response.UserNewPublication
import com.example.diyapp.data.adapter.user.SessionManager
import com.example.diyapp.databinding.FragmentNewPublicationBinding
import com.example.diyapp.domain.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewPublicationFragment : Fragment() {

    private var _binding: FragmentNewPublicationBinding? = null
    private val binding get() = _binding!!
    private val imageUris = mutableListOf<Uri>()
    private lateinit var recyclerViewAdapter: MultipleImagesAdapter

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

        setupSpinner()
        setupRecyclerView()
        setupImagePickers()
        setupListeners()
    }

    private fun validateFields(): Boolean {
        val isTitleValid = binding.etTitle.text.toString().isNotEmpty()
        val isDescriptionValid = binding.etDescription.text.toString().isNotEmpty()
        val isInstructionsValid = binding.etInstructions.text.toString().isNotEmpty()
        val isMainPhotoSet = binding.imageViewMainPhoto.drawable != null
        val areImagesSelected = recyclerViewAdapter.itemCount > 0

        return isTitleValid && isDescriptionValid && isInstructionsValid && isMainPhotoSet && areImagesSelected
    }

    private fun onPostButtonClick() {
        if (validateFields()) {

            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val theme = binding.spinnerOptions.selectedItem.toString()
            val instructions = binding.etInstructions.text.toString()
            val mainPhoto = ImageUtils.bitmapToBase64(binding.imageViewMainPhoto.drawToBitmap())
            val photos = recyclerViewAdapter.getImagesAsBase64(requireContext())

            createPublication(title, theme, mainPhoto, description, instructions, photos)

        } else {
            SessionManager.showToast(requireContext(), R.string.fillFields)
        }
    }

    private fun setupSpinner() {
        val options = resources.getStringArray(R.array.category_options)
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOptions.adapter = spinnerAdapter
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = MultipleImagesAdapter(imageUris)
        binding.recyclerViewInstructionPhotos.apply {
            adapter = recyclerViewAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupImagePickers() {
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.imageViewMainPhoto.setImageURI(uri)
                }
            }

        val pickMedia2 =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                if (uris.isNotEmpty()) {
                    val startIndex = imageUris.size
                    imageUris.clear()
                    recyclerViewAdapter.notifyItemRangeRemoved(0, startIndex)
                    imageUris.addAll(uris)
                    recyclerViewAdapter.notifyItemRangeInserted(0, uris.size)
                }
            }

        binding.btnUploadImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnUploadMultipleImages.setOnClickListener {
            pickMedia2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun setupListeners() {
        binding.btnPost.setOnClickListener { onPostButtonClick() }
    }

    private fun createPublication(
        title: String,
        theme: String,
        photoMain: String,
        description: String,
        instructions: String,
        photoProcess: List<String>
    ) {

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val sharedPref = SessionManager.getUserInfo(requireContext())
                val email = sharedPref["email"]
                val creation = UserNewPublication(
                    email!!,
                    title,
                    theme,
                    photoMain,
                    instructions,
                    description,
                    0,
                    photoProcess
                )
                val call = RetrofitManager.getRetroFit().create(APIService::class.java)
                    .createPublication(creation)

                val responseBody: ServerResponse = call.body()!!

                withContext(Dispatchers.Main) {

                    if (call.isSuccessful) {
                        if (responseBody.message.isNotEmpty()) {
                            SessionManager.showToast(requireContext(), R.string.publicationCreated)
                            withContext(Dispatchers.Main) {
                                findNavController().navigate(R.id.myPublicationsFragment)
                            }
                        }
                    } else {
                        SessionManager.showToast(requireContext(), R.string.error2)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    SessionManager.showToast(requireContext(), R.string.error)
                }
                Log.e("API Error", "Error: ${e.message}")
            }
        }
    }
}