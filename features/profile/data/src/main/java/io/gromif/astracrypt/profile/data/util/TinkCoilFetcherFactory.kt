package io.gromif.astracrypt.profile.data.util

import coil3.ImageLoader
import coil3.decode.DataSource
import coil3.decode.ImageSource
import coil3.fetch.FetchResult
import coil3.fetch.Fetcher
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.crypto.tink.keyset.KeysetTemplates
import okio.FileSystem
import okio.buffer
import okio.source

class TinkCoilFetcherFactory(
    private val settingsRepository: SettingsRepository,
    private val fileUtil: FileUtil
) : Fetcher.Factory<Avatar.External> {
    override fun create(data: Avatar.External, options: Options, imageLoader: ImageLoader) =
        TinkCoilFetcher()

    inner class TinkCoilFetcher : Fetcher {
        override suspend fun fetch(): FetchResult {
            val aead = settingsRepository.getAvatarAead()
            val inputStream = fileUtil.openInputStream(
                aeadTemplate = KeysetTemplates.Stream.entries.getOrNull(aead)
            )
            return SourceFetchResult(
                source = ImageSource(
                    source = inputStream.source().buffer(),
                    fileSystem = FileSystem.SYSTEM
                ),
                mimeType = null,
                dataSource = DataSource.DISK
            )
        }
    }
}
