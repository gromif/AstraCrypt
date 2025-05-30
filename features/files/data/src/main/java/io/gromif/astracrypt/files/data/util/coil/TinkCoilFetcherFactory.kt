package io.gromif.astracrypt.files.data.util.coil

import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.request.Options
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import okio.buffer
import okio.source
import java.io.File

class TinkCoilFetcherFactory(
    private val fileHandler: FileHandler,
    private val associatedDataManager: AssociatedDataManager,
    private val cacheDir: File
) : Fetcher.Factory<FileSource> {
    override fun create(data: FileSource, options: Options, imageLoader: ImageLoader) =
        TinkCoilFetcher(data)

    inner class TinkCoilFetcher(private val data: FileSource) : Fetcher {
        override suspend fun fetch(): FetchResult {
            val file = fileHandler.getFilePath(relativePath = data.path)
            val aead = KeysetTemplates.Stream.entries.getOrNull(index = data.aeadIndex)
            val sourceInputChannel = aead?.let {
                fileHandler.getPreviewStreamingAead(parameters = aead.params).newDecryptingStream(
                    /* ciphertextSource = */ file.inputStream(),
                    /* associatedData = */ associatedDataManager.getAssociatedData()
                )
            } ?: file.inputStream()
            return SourceResult(
                source = ImageSource(
                    source = sourceInputChannel.source().buffer(), cacheDir
                ),
                mimeType = null,
                dataSource = DataSource.DISK
            )
        }
    }

}