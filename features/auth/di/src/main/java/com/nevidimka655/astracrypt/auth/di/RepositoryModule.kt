package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.data.RepositoryImpl
import com.nevidimka655.astracrypt.auth.data.TinkRepositoryImpl
import com.nevidimka655.astracrypt.auth.data.datastore.AuthDataStoreManager
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.TinkRepository
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.crypto.tink.core.GetGlobalAssociatedDataPrf
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.data.AssociatedDataManager
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @Provides
    fun provideRepository(
        authDataStoreManager: AuthDataStoreManager,
        authToAuthDtoMapper: Mapper<Auth, AuthDto>,
        authDtoToAuthMapper: Mapper<AuthDto, Auth>
    ): Repository = RepositoryImpl(
        authDataStoreManager = authDataStoreManager,
        authToAuthDtoMapper = authToAuthDtoMapper,
        authDtoToAuthMapper = authDtoToAuthMapper
    )

    @Provides
    fun provideTinkRepository(
        keysetManager: KeysetManager,
        associatedDataManager: AssociatedDataManager,
        getGlobalAssociatedDataPrf: GetGlobalAssociatedDataPrf
    ): TinkRepository = TinkRepositoryImpl(
        keysetManager = keysetManager,
        associatedDataManager = associatedDataManager,
        getGlobalAssociatedDataPrf = getGlobalAssociatedDataPrf
    )

}