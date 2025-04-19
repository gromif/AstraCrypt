package io.gromif.astracrypt.utils.mapper

import android.net.Uri
import androidx.core.net.toUri
import io.gromif.astracrypt.utils.Mapper

class StringToUriMapper: Mapper<String, Uri> {
    override fun invoke(item: String): Uri {
        return item.toUri()
    }
}