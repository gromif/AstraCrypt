package io.gromif.astracrypt.files.data.io

import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.request.Options
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.streamingAead
import io.gromif.astracrypt.files.domain.model.FileSource
import okio.buffer
import okio.source
import java.io.File

class TinkCoilFetcherFactory(
    private val keysetManager: KeysetManager,
    private val filesUtil: FilesUtil
) : Fetcher.Factory<FileSource> {
    override fun create(data: FileSource, options: Options, imageLoader: ImageLoader) =
        TinkCoilFetcher(data)

    inner class TinkCoilFetcher(private val data: FileSource) : Fetcher {
        override suspend fun fetch(): FetchResult {
            val file = File(data.path)
            val aead = KeysetTemplates.Stream.entries.getOrNull(index = data.aeadIndex)
            val sourceInputChannel = aead?.let {
                keysetManager.stream(it).streamingAead()
                    .newDecryptingStream(file.inputStream(), keysetManager.associatedData)
            } ?: file.inputStream()
            return SourceResult(
                source = ImageSource(
                    source = sourceInputChannel.source().buffer(), filesUtil.cacheDir
                ),
                mimeType = null,
                dataSource = DataSource.DISK
            )
        }
    }

}