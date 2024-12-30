package com.nevidimka655.astracrypt.data.io

import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.request.Options
import com.nevidimka655.astracrypt.data.crypto.AeadManager
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.extensions.streamingAeadPrimitive
import okio.buffer
import okio.source
import java.io.File
import javax.inject.Inject

class TinkCoilFetcherFactory @Inject constructor(
    private val keysetManager: KeysetManager,
    private val aeadManager: AeadManager,
    private val filesUtil: FilesUtil
) : Fetcher.Factory<CoilTinkModel> {
    override fun create(data: CoilTinkModel, options: Options, imageLoader: ImageLoader) =
        TinkCoilFetcher(data)

    inner class TinkCoilFetcher(private val data: CoilTinkModel) : Fetcher {
        override suspend fun fetch(): FetchResult {
            val aeadInfo = aeadManager.getInfo()
            val path = data.absolutePath ?: "${filesUtil.dataDir}/${data.path}"
            val file = File(path)
            val sourceInputChannel = aeadInfo.preview?.let {
                keysetManager.stream(it).streamingAeadPrimitive()
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