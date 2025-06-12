package io.gromif.astracrypt.files.files.model.action.factory

import android.net.Uri
import io.gromif.astracrypt.files.files.FilesViewModel
import io.gromif.astracrypt.files.files.model.action.Actions
import io.gromif.astracrypt.files.files.model.action.ImportActions
import io.gromif.astracrypt.resources.R

internal fun Actions.createImportActions(
    vm: FilesViewModel,
    onScan: () -> Unit,
    onMessage: (Int) -> Unit
): ImportActions = object : ImportActions() {
    override fun scan() = onScan()

    override fun import(uriList: Array<Uri>, saveSource: Boolean) {
        vm.import(
            *uriList,
            saveSource = saveSource,
            onSuccess = { onMessage(R.string.snack_imported) },
            onError = { onMessage(R.string.error) }
        )
    }
}
