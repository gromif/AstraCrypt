package com.nevidimka655.astracrypt.view.composables.components.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

class DatabaseTransformDialog : DialogFragment() {

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return MaterialAlertDialogBuilder(
            requireActivity()
        )
            .setTitle(R.string.dialog_dbUpdate)
            .setIcon(R.drawable.ic_db_sync)
            .setView(onCreateView(layoutInflater, null, savedInstanceState))
            .create()
    }*/

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
}