package io.gromif.astracrypt.profile.data.util

import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.request.Options
import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.crypto.tink.domain.KeysetTemplates
import okio.buffer
import okio.source
import java.io.File

class TinkCoilFetcherFactory(
    private val settingsRepository: SettingsRepository,
    private val fileUtil: FileUtil,
    private val cacheDir: File
) : Fetcher.Factory<Avatar.External> {
    override fun create(data: Avatar.External, options: Options, imageLoader: ImageLoader) =
        TinkCoilFetcher()

    inner class TinkCoilFetcher : Fetcher {
        override suspend fun fetch(): FetchResult {
            val aead = settingsRepository.getAvatarAead()
            val inputStream = fileUtil.openInputStream(
                aeadTemplate = KeysetTemplates.Stream.entries.getOrNull(aead)
            )
            return SourceResult(
                source = ImageSource(
                    source = inputStream.source().buffer(), cacheDir
                ),
                mimeType = null,
                dataSource = DataSource.DISK
            )
        }
    }

}