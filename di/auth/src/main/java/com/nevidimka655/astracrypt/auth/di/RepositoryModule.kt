package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.data.RepositoryImpl
import com.nevidimka655.astracrypt.auth.data.datastore.AuthDataStoreManager
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.core.hash.Sha384Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        keysetManager: KeysetManager,
        authDataStoreManager: AuthDataStoreManager,
        authToAuthDtoMapper: Mapper<Auth, AuthDto>,
        authDtoToAuthMapper: Mapper<AuthDto, Auth>,
        base64Util: Base64Util
    ): Repository = RepositoryImpl(
        keysetManager = keysetManager,
        authDataStoreManager = authDataStoreManager,
        authToAuthDtoMapper = authToAuthDtoMapper,
        authDtoToAuthMapper = authDtoToAuthMapper,
        base64Util = base64Util
    )

}