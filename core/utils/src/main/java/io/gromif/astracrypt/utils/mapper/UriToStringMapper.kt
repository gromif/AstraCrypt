package io.gromif.astracrypt.utils.mapper

import android.net.Uri
import io.gromif.astracrypt.utils.Mapper

class UriToStringMapper: Mapper<Uri, String> {
    override fun invoke(item: Uri): String {
        return item.toString()
    }
}