package com.nevidimka655.astracrypt.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R

class DeleteOriginalFilesDialog : DialogFragment(R.layout.dialog_transform_db) {
    private val vm by activityViewModels<MainVM>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return MaterialAlertDialogBuilder(
            requireActivity(),
            com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered
        )
            .setTitle(R.string.dialog_deleteOriginalFiles)
            .setIcon(R.drawable.ic_delete_outline)
            .setMessage(R.string.dialog_deleteOriginalFiles_msg)
            .setPositiveButton(R.string.files_options_delete) { _, _ ->
                startImportProcess()
            }
            .setNegativeButton(R.string.save) { _, _ ->
                startImportProcess(true)
            }
            .setNeutralButton(android.R.string.cancel) { _, _ ->
                vm.lastUriListToImport = null
            }
            .create()
    }

    private fun startImportProcess(saveOriginalFiles: Boolean = false) {
        vm.lastUriListToImport?.let {
            vm.import(
                uriList = it.toTypedArray(),
                saveOriginalFiles = saveOriginalFiles
            )
        }
    }

}