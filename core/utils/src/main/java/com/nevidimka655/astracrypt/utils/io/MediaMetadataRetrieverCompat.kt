package com.nevidimka655.astracrypt.utils.io

import android.media.MediaMetadataRetriever

class MediaMetadataRetrieverCompat: MediaMetadataRetriever(), AutoCloseable {

    override fun close() {
        release()
    }

}