package com.nevidimka655.astracrypt.utils.mapper

import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper

class UriToStringMapper: Mapper<Uri, String> {
    override fun invoke(item: Uri): String {
        return item.toString()
    }
}