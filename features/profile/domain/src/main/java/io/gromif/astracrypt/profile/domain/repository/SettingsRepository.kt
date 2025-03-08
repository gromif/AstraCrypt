package io.gromif.astracrypt.profile.domain.repository

import io.gromif.astracrypt.profile.domain.model.AeadMode
import io.gromif.astracrypt.profile.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val profileFlow: Flow<Profile>

    suspend fun getProfile(): Profile
    suspend fun setProfile(profile: Profile)

    suspend fun getAvatarAead(): Int
    suspend fun setAvatarAead(aead: Int)

    suspend fun setAead(aeadMode: AeadMode)
    fun getAeadFlow(): Flow<AeadMode>

}