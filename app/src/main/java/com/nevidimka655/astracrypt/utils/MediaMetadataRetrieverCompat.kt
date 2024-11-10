package com.nevidimka655.astracrypt.utils

import android.media.MediaMetadataRetriever

class MediaMetadataRetrieverCompat: MediaMetadataRetriever(), AutoCloseable {

    override fun close() {
        release()
    }

}