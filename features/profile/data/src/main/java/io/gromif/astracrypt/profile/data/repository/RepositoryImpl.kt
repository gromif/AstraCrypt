package io.gromif.astracrypt.profile.data.repository

import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.profile.data.util.ExternalIconUtil
import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.crypto.tink.domain.KeysetTemplates

class RepositoryImpl(
    private val externalIconUtil: ExternalIconUtil,
    private val uriMapper: Mapper<String, Uri>,
) : Repository {

    override suspend fun importAvatar(
        aead: Int,
        path: String,
        quality: Int,
        size: Int,
    ) {
        val uri = uriMapper(path)
        externalIconUtil.saveFromUri(
            uri = uri,
            quality = quality,
            size = size,
            aead = aead
        )
    }

    override suspend fun changeAvatarAead(oldAead: Int, newAead: Int) {
        externalIconUtil.changeAead(
            oldAead = KeysetTemplates.Stream.entries.getOrNull(oldAead),
            newAead = KeysetTemplates.Stream.entries.getOrNull(newAead)
        )
    }

}