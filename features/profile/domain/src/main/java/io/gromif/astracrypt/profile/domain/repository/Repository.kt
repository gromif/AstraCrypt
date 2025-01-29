package io.gromif.astracrypt.profile.domain.repository

interface Repository {

    suspend fun importAvatar(
        aead: Int,
        path: String,
        quality: Int,
        size: Int,
    )

    suspend fun changeAvatarAead(oldAead: Int, newAead: Int)

}