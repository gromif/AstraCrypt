package io.gromif.astracrypt.auth.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.data.dto.AuthDto
import io.gromif.astracrypt.auth.data.repository.SettingsRepositoryImpl
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.utils.Mapper
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.tink_datastore.TinkDataStore

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @ViewModelScoped
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