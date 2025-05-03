package io.gromif.astracrypt.profile.di.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.data.repository.SettingsRepositoryImpl
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.astracrypt.utils.Mapper
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.keyset.KeysetManager
import io.gromif.tink_datastore.TinkDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SettingsRepositoryModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(
        dataStore: DataStore<Preferences>,
        keysetManager: KeysetManager,
        base64Encoder: Base64Encoder,
        profileMapper: Mapper<ProfileDto, Profile>,
        profileDtoMapper: Mapper<Profile, ProfileDto>,
    ): SettingsRepository = SettingsRepositoryImpl(
        dataStore = dataStore,
        keysetManager = keysetManager,
        profileMapper = profileMapper,
        profileDtoMapper = profileDtoMapper,
        tinkDataStoreParams = TinkDataStore.Params(
            purpose = "profile",
            keyAD = null,
            valueAD = null
        ),
        encoder = base64Encoder,
    )

}