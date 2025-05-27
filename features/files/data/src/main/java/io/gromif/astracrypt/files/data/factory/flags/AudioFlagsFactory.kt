package io.gromif.astracrypt.files.data.factory.flags

import android.content.ContentResolver
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.utils.Mapper

class AudioFlagsFactory(
    private val contentResolver: ContentResolver,
    private val uriMapper: Mapper<String, Uri>,
) {

    fun create(path: String): FileFlagsDto.Audio? {
        val uri = uriMapper(path)
        val defaultAudioFlags = FileFlagsDto.Audio()

        var sampleRate = defaultAudioFlags.sampleRate
        contentResolver.openFileDescriptor(uri, "r")?.use { fd ->
            val audio = MediaExtractor()
            try {
                audio.setDataSource(fd.fileDescriptor)

                val track = audio.getTrackFormat(0)
                sampleRate = track.getInteger(MediaFormat.KEY_SAMPLE_RATE)
            } finally {
                audio.release()
            }
        }
        val audioFlags = FileFlagsDto.Audio(sampleRate = sampleRate)
        return audioFlags.takeIf { it != defaultAudioFlags }
    }
}
