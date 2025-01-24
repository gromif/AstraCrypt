package io.gromif.astracrypt.files.model.action

import android.net.Uri

interface FilesNavActions {

    companion object {

        internal val Empty = object : FilesNavActions {
            override fun toFiles(id: Long, name: String) {}
            override fun toExport(id: Long, output: Uri) {}
            override fun toDetails(id: Long) {}
        }

    }

    fun toFiles(id: Long, name: String)

    fun toExport(id: Long, output: Uri)

    fun toDetails(id: Long)

}