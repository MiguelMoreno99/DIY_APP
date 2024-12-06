package com.example.diyapp.ui.newpublication

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyapp.R
import com.example.diyapp.data.SessionManager
import com.example.diyapp.data.adapter.create.ImageUtils
import com.example.diyapp.data.adapter.create.MultipleImagesAdapter
import com.example.diyapp.databinding.FragmentNewPublicationBinding
import com.example.diyapp.ui.viewmodel.NewPublicationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewPublicationFragment : Fragment() {

    private var _binding: FragmentNewPublicationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel: NewPublicationViewModel by viewModels()
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

        observeViewModel()

        val sharedPref = sessionManager.getUserInfo(requireContext())
        viewModel.setUserEmail(sharedPref["email"] ?: "")
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setupImagePickers() {
        val pickMainPhoto =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let { binding.imageViewMainPhoto.setImageURI(it) }
            }

        val pickMultiplePhotos =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
                if (uris.isNotEmpty()) {
                    imageUris.clear()
                    imageUris.addAll(uris)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }

        binding.btnUploadImage.setOnClickListener {
            pickMainPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnUploadMultipleImages.setOnClickListener {
            pickMultiplePhotos.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun setupListeners() {
        binding.btnPost.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val theme = binding.spinnerOptions.selectedItem.toString()
            val instructions = binding.etInstructions.text.toString()
            val mainPhoto = ImageUtils.bitmapToBase64(binding.imageViewMainPhoto.drawToBitmap())
            val photos = recyclerViewAdapter.getImagesAsBase64(requireContext())
            lifecycleScope.launch {
                viewModel.createPublication(
                    title,
                    description,
                    theme,
                    instructions,
                    mainPhoto,
                    photos
                )
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isPublicationCreated.observe(viewLifecycleOwner) { success ->
            if (success) {
                sessionManager.showToast(requireContext(), R.string.publicationCreated)
                findNavController().navigate(R.id.myPublicationsFragment)
            } else {
                sessionManager.showToast(requireContext(), R.string.error2)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { messageResId ->
            messageResId?.let {
                sessionManager.showToast(requireContext(), it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}