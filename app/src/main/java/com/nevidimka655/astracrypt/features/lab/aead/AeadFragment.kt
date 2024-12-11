package com.nevidimka655.astracrypt.features.lab.aead

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.nevidimka655.astracrypt.view.ui.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.databinding.FragmentLabBinding
import com.nevidimka655.astracrypt.app.utils.billing.AlgorithmPaywallListFactory
import com.nevidimka655.astracrypt.view.ui.extensions.setTooltip
import com.nevidimka655.astracrypt.view.ui.extensions.viewLifecycleScope
import com.nevidimka655.astracrypt.app.extensions.withViewLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.random.Random

class AeadFragment : androidx.fragment.app.Fragment(R.layout.fragment_lab) {
    private val vm by activityViewModels<MainVM>()
    private val labManager get() = vm.toolsManager.labManager
    private var binding: FragmentLabBinding? = null

    private val dataTypeItems by lazy {
        resources.getStringArray(R.array.lab_data_types)
    }
    private lateinit var itemsMajor: Array<String>
    private lateinit var itemsAead: Array<String>
    private val saveKeysetContract = registerForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        if (uri != null) viewLifecycleScope.launch(Dispatchers.IO) {
            labManager.saveKeyFile(requireContext().contentResolver, uri)
        }
    }
    private val openKeysetContract = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            labManager.keysetUri = uri
            updateUi()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLabBinding.bind(view).apply {
            password.run {
                editText?.doOnTextChanged { text, _, _, _ ->
                    if (text != null) {
                        val newPassword = text.toString()
                        val isPasswordAccepted = labManager.newKeysetPassword(newPassword)
                        if (isPasswordAccepted && labManager.keysetUri != null) shuffleKey()
                    }
                }
            }
            (dataType.editText as MaterialAutoCompleteTextView).run {
                /*setSimpleItems(dataTypeItems)*/
                val pos = labManager.dataType.ordinal
                setText(dataTypeItems[pos], false)
                setOnItemClickListener { _, _, position, _ ->
                    if (position == 1 && dataTypeItems[1].contains("(")) {
                        setText(dataTypeItems[0], false)
                        return@setOnItemClickListener
                    }
                    labManager.run {
                        dataType = LabAeadManager.DataType.entries[position]
                        encryptionType = 0
                    }
                    shuffleKey()
                    setupEncryptionTypes(binding?.encryptionType)
                }
            }
            /*requireMainActivity().fabLarge.setOnClickListener { done() }*/
            shuffle.setOnClickListener {
                shuffleKey()
            }
            save.setOnClickListener {
                saveKeysetContract.launch("ac_key_${abs(Random.nextInt())}.txt")
            }
            load.setOnClickListener {
                openKeysetContract.launch(arrayOf("text/plain"))
            }
            save.setTooltip(R.string.save)
            load.setTooltip(R.string.load)
        }
        updateUi()
        labManager.keyStateFlow.withViewLifecycle(viewLifecycleOwner) {
            if (it != null) binding?.run {
                keysetEt.setText(it.encryptedKeyset)
            }
        }
    }

    private fun updateUi() {
        val state = labManager.keysetUri != null
        binding?.run {
            save.isEnabled = !state
            dataType.isVisible = !state
            encryptionType.isVisible = !state
            keyset.isVisible = !state
        }
    }

    private fun setupEncryptionTypes(encryptionType: TextInputLayout?) {
        if (encryptionType == null) return
        (encryptionType.editText as MaterialAutoCompleteTextView).run {
            val dataType = labManager.dataType
            val items = if (dataType == LabAeadManager.DataType.Text) itemsAead else itemsMajor
            /*setSimpleItems(items)*/
            val pos = labManager.encryptionType
            setText(items[pos], false)
            setOnItemClickListener { _, _, position, _ ->
                labManager.encryptionType = position
                if (labManager.keysetUri == null) shuffleKey()
            }
        }
    }

    private fun shuffleKey() {
        labManager.keysetUri = null
        updateUi()
        viewLifecycleScope.launch { labManager.shuffleKey() }
    }

    private fun done() = viewLifecycleScope.launch(Dispatchers.Main) {
        var dataType = labManager.dataType.ordinal
        if (labManager.keysetUri != null) {
            val contentResolver = requireContext().contentResolver
            val isDecrypted = withContext(Dispatchers.Default) {
                labManager.decryptLoadedKeyset(contentResolver)
            }
            binding?.run {
                password.error =
                    if (isDecrypted) null else getString(R.string.t_invalidPass)
            }
            if (!isDecrypted) return@launch
            val currentLabKey = labManager.currentLabKey
            dataType = currentLabKey!!.dataType
        }
        when (dataType) {
            LabAeadManager.DataType.Text.ordinal -> findNavController().navigate(
                R.id.action_aeadFragment_to_aeadTextFragment
            )
            LabAeadManager.DataType.Files.ordinal -> findNavController().navigate(
                R.id.action_aeadFragment_to_aeadFilesFragment
            )
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleScope.launch {
            itemsMajor = AlgorithmPaywallListFactory.fetchItemsMajor(
            ).toTypedArray()
            itemsAead = AlgorithmPaywallListFactory.fetchItemsAead(
            ).toTypedArray()
            if (labManager.keysetUri == null && labManager.currentLabKey == null) shuffleKey()
            withContext(Dispatchers.Main) {
                setupEncryptionTypes(binding?.encryptionType)
            }
        }
    }

}