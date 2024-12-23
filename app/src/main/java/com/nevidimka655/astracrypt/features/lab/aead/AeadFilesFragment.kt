package com.nevidimka655.astracrypt.features.lab.aead

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkInfo
import com.nevidimka655.astracrypt.databinding.FragmentLabFilesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AeadFilesFragment : Fragment() {
    private val labManager: LabAeadManager get() = TODO()
    private var binding: FragmentLabFilesBinding? = null

    private val openFileContract = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) {
        if (it != null) {
            val documentFile = DocumentFile.fromSingleUri(requireContext(), it)
            labManager.run {
                filesSourceUri = it
                filesSourceName = documentFile?.name
                binding?.sourceText?.editText?.setText(filesSourceName)
            }
            updateButtonsState()
        }
    }
    private val openDestinationContract = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) {
        if (it != null) {
            val documentFile = DocumentFile.fromTreeUri(requireContext(), it)
            labManager.run {
                filesDestinationUri = it
                filesDestinationName = documentFile?.name
                binding?.destinationText?.editText?.setText(filesDestinationName)
            }
            updateButtonsState()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLabFilesBinding.bind(view).apply {
            encrypt.setOnClickListener { startFilesWorker(true) }
            decrypt.setOnClickListener { startFilesWorker(false) }
            updateFieldsText(this)
            sourceText.setEndIconOnClickListener {
                openFileContract.launch(arrayOf("*/*"))
            }
            destinationText.setEndIconOnClickListener {
                openDestinationContract.launch(null)
            }
        }
        registerWorkerInfoLiveData()
        updateDetailsCard()
        updateButtonsState()
    }

    private fun startFilesWorker(encryptionMode: Boolean) =
        lifecycleScope.launch(Dispatchers.Main) {
            labManager.startFilesWorker(
                encryptionMode = encryptionMode,
                associatedData = getAssociatedData()
            )
            registerWorkerInfoLiveData()
            updateButtonsState()
        }

    private fun updateFieldsText(binding: FragmentLabFilesBinding) = with(binding) {
        sourceText.editText?.setText(labManager.filesSourceName)
        destinationText.editText?.setText(labManager.filesDestinationName)
    }

    private fun updateDetailsCard(workInfo: WorkInfo? = null) {
        binding?.run {
            detailsCard.isVisible = labManager.filesSourceName != null
            detailsFile.text =
                labManager.filesSourceName
            detailsProgress.isVisible = workInfo?.state == WorkInfo.State.RUNNING
        }
    }

    private fun updateButtonsState() {
        val isButtonsEnabled = labManager.isFilesTabSetupOk
        binding?.run {
            encrypt.isEnabled = isButtonsEnabled
            decrypt.isEnabled = isButtonsEnabled
        }
    }

    private fun registerWorkerInfoLiveData() {
        labManager.filesWorkerInfoLiveData!!.observe(viewLifecycleOwner) {
            if (it != null) {
                parseWorkInfo(it)
            }
        }
    }

    private fun parseWorkInfo(info: WorkInfo) {
        when (info.state) {
            WorkInfo.State.SUCCEEDED -> {
                labManager.filesWorkerInfoLiveData = null
            }
            WorkInfo.State.FAILED -> {
                //requireMainActivity().showSnackbarIfPossible(R.string.error)
                labManager.filesWorkerInfoLiveData = null
            }
            else -> {}
        }
        updateButtonsState()
        updateDetailsCard(info)
    }

    private fun getAssociatedData() =
        binding?.run { associatedData.editText!!.text.toString() } ?: ""

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}