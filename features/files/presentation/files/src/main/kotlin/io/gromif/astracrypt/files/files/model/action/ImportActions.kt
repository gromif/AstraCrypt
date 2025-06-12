package io.gromif.astracrypt.files.files.model.action

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
internal open class ImportActions {
    open fun import(uriList: Array<Uri>, saveSource: Boolean) {}
    open fun scan() {}
}
