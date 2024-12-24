package com.nevidimka655.astracrypt.utils.mapper

import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper

class StringToUriMapper: Mapper<String, Uri> {
    override fun invoke(item: String): Uri {
        return Uri.parse(item)
    }
}