package io.gromif.astracrypt.auth.di.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.auth.data.dto.AuthDto
import io.gromif.astracrypt.auth.data.repository.SettingsRepositoryImpl
import io.gromif.astracrypt.auth.di.AuthDataStore
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
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
        @AuthDataStore
        dataStore: DataStore<Preferences>,
        base64Encoder: Base64Encoder,
        keysetManager: KeysetManager,
        authDtoMapper: Mapper<Auth, AuthDto>,
        authMapper: Mapper<AuthDto, Auth>
    ): SettingsRepository = SettingsRepositoryImpl(
        dataStore = dataStore,
        encoder = base64Encoder,
        keysetManager = keysetManager,
        tinkDataStoreParams = TinkDataStore.Params(purpose = "auth"),
        authMapper = authMapper,
        authDtoMapper = authDtoMapper
    )

}