package io.gromif.astracrypt.files.data.factory.flags

import android.content.ContentResolver
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.dto.FileFlagsDto

class AudioFlagsFactory(
    private val contentResolver: ContentResolver,
    private val uriMapper: Mapper<String, Uri>,
) {

    fun create(path: String): FileFlagsDto.Audio? {
        val uri = uriMapper(path)
        val defaultAudioFlags = FileFlagsDto.Audio()

        var sampleRate = defaultAudioFlags.sampleRate
        contentResolver.openFileDescriptor(uri, "r")?.use {
            val mediaExtractor = MediaExtractor().apply { setDataSource(it.fileDescriptor) }
            val track = mediaExtractor.getTrackFormat(0)
            sampleRate = track.getInteger(MediaFormat.KEY_SAMPLE_RATE)
        }
        val audioFlags = FileFlagsDto.Audio(sampleRate = sampleRate)
        return audioFlags.takeIf { it != defaultAudioFlags }
    }

}