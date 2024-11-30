package com.nevidimka655.astracrypt.utils

import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.request.Options
import com.nevidimka655.astracrypt.model.CoilTinkModel
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.streamingAeadPrimitive
import okio.buffer
import okio.source
import java.io.File
import javax.inject.Inject

class TinkCoilFetcherFactory @Inject constructor(
    private val io: Io
) : Fetcher.Factory<CoilTinkModel> {
    override fun create(data: CoilTinkModel, options: Options, imageLoader: ImageLoader) =
        TinkCoilFetcher(data)

    inner class TinkCoilFetcher(private val data: CoilTinkModel) : Fetcher {
        override suspend fun fetch(): FetchResult {
            val streamOrdinal = data.encryptionType
            val path = data.absolutePath ?: "${io.dataDir}/${data.path}"
            val file = File(path)
            val sourceInputChannel = if (streamOrdinal == -1) file.inputStream()
            else {
                val keysetHandle = KeysetFactory.stream(Engine.appContext, KeysetTemplates.Stream.entries[streamOrdinal])
                keysetHandle.streamingAeadPrimitive()
                    .newDecryptingStream(file.inputStream(), KeysetFactory.associatedData)
            }
            return SourceResult(
                source = ImageSource(
                    source = sourceInputChannel.source().buffer(), io.cacheDir
                ),
                mimeType = null,
                dataSource = DataSource.DISK
            )
        }
    }

}