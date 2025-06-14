package io.gromif.astracrypt.files.recent.list

import androidx.compose.runtime.Immutable

@Immutable
open class Actions {

    open fun openFile(id: Long) {}

    open fun openFolder(id: Long, name: String) {}
}
