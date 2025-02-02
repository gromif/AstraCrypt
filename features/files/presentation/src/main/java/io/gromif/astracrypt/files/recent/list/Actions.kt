package io.gromif.astracrypt.files.recent.list

import androidx.compose.runtime.Immutable

@Immutable
interface Actions {

    fun openFile(id: Long)

    fun openFolder(id: Long, name: String)

    companion object {

        internal val default = object : Actions {
            override fun openFile(id: Long) {}
            override fun openFolder(id: Long, name: String) {}
        }

    }

}