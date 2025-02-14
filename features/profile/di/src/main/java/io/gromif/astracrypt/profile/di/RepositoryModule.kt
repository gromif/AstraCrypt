package io.gromif.astracrypt.profile.di

import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.data.repository.RepositoryImpl
import io.gromif.astracrypt.profile.data.repository.SettingsRepositoryImpl
import io.gromif.astracrypt.profile.data.util.ExternalIconUtil
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import io.gromif.astracrypt.utils.Mapper
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.tink_datastore.TinkDataStore

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(
        externalIconUtil: ExternalIconUtil,
        uriMapper: Mapper<String, Uri>,
    ): Repository = RepositoryImpl(
        externalIconUtil = externalIconUtil,
        uriMapper = uriMapper,
    )

    @ViewModelScoped
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