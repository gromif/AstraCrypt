package com.nevidimka655.astracrypt.view.composables.shared.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.databinding.DialogTransformDbBinding

class DatabaseTransformDialog : DialogFragment(R.layout.dialog_transform_db) {
    private var binding: DialogTransformDbBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return MaterialAlertDialogBuilder(
            requireActivity()
        )
            .setTitle(R.string.dialog_dbUpdate)
            .setIcon(R.drawable.ic_db_sync)
            .setView(onCreateView(layoutInflater, null, savedInstanceState))
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding?.root ?: DialogTransformDbBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*WorkerFactory.transformWorkLiveData?.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.state.isFinished) with(vm) {
                    WorkerFactory.transformWorkLiveData = null
                    pagingSource?.invalidate()
                    pagingStarredSource?.invalidate()
                    dismiss()
                }
            }
        }*/
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}