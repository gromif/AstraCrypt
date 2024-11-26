package com.nevidimka655.astracrypt.ui.dialogs

import androidx.fragment.app.DialogFragment

class ExportDialog : DialogFragment() {

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
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
                OpenManager.job?.cancel()
                IO.clearExportedCache()
                d.dismiss()
            }
        }.create().also { alert ->
            alert.setOnShowListener {
                OpenManager.uiState.withViewLifecycle(viewLifecycleOwner) { state ->
                    binding?.run {
                        itemName.text = state.name
                        progressBar.isVisible = state.progress != state.itemsCount
                        buttonOpen?.isEnabled = state.progress == state.itemsCount
                    }
                }
                buttonOpen = alert.getButton(DialogInterface.BUTTON_POSITIVE).apply {
                    isEnabled = OpenManager.uiState.value.progress == 1
                    setOnClickListener {
                        val intentView = Intent(Intent.ACTION_VIEW).apply {
                            data = OpenManager.uiState.value.lastOutputFile
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        try {
                            startActivity(intentView)
                        } catch (ignored: Exception) {}
                    }
                }
            }
        }
    }*/

}