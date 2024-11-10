package com.nevidimka655.astracrypt.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.databinding.DialogExtractingBinding
import com.nevidimka655.astracrypt.utils.ColorManager
import com.nevidimka655.astracrypt.utils.IO
import com.nevidimka655.astracrypt.utils.extensions.withViewLifecycle

class ExportDialog : DialogFragment() {

    private val vm by activityViewModels<MainVM>()
    private val openManager get() = vm.openManager

    private var binding: DialogExtractingBinding? = null

    private var buttonOpen: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        val icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_export, null)?.apply {
            setTint(ColorManager.colorSecondary)
        }
        return MaterialAlertDialogBuilder(
            requireActivity(),
            com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
        ).apply {
            setTitle(R.string.dialog_exporting)
            setIcon(icon)
            setView(onCreateView(layoutInflater, null, savedInstanceState))
            setPositiveButton(R.string.open) { d, _ ->
                d.dismiss()
            }
            setNegativeButton(android.R.string.cancel) { d, _ ->
                openManager.job?.cancel()
                IO.clearExportedCache()
                d.dismiss()
            }
        }.create().also { alert ->
            alert.setOnShowListener {
                openManager.uiState.withViewLifecycle(viewLifecycleOwner) { state ->
                    binding?.run {
                        itemName.text = state.name
                        progressBar.isVisible = state.progress != state.itemsCount
                        buttonOpen?.isEnabled = state.progress == state.itemsCount
                    }
                }
                buttonOpen = alert.getButton(DialogInterface.BUTTON_POSITIVE).apply {
                    isEnabled = openManager.uiState.value.progress == 1
                    setOnClickListener {
                        val intentView = Intent(Intent.ACTION_VIEW).apply {
                            data = openManager.uiState.value.lastOutputFile
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        try {
                            startActivity(intentView)
                        } catch (ignored: Exception) {}
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding?.root ?: DialogExtractingBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onDestroyView() {
        buttonOpen = null
        binding = null
        super.onDestroyView()
    }

}