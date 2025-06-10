package io.gromif.astracrypt.files.files.model.action

import android.net.Uri
import androidx.compose.runtime.Stable

@Stable
open class FilesNavActions {

    open fun toFiles(id: Long, name: String) {}

    open fun toExport(id: Long, output: Uri) {}

    open fun toExportPrivately(id: Long) {}

    open fun toDetails(id: Long) {}
}
