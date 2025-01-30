package com.nevidimka655.astracrypt.auth.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.data.repository.SettingsRepositoryImpl
import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.repository.SettingsRepository
import com.nevidimka655.astracrypt.utils.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.crypto.tink.encoders.Base64Encoder
import io.gromif.tink_datastore.TinkDataStore

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @Provides
    fun provideRepository(
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