package com.nevidimka655.astracrypt.app.utils

import android.media.MediaMetadataRetriever

class MediaMetadataRetrieverCompat: MediaMetadataRetriever(), AutoCloseable {

    override fun close() {
        release()
    }

}