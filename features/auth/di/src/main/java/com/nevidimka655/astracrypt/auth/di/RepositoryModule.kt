package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.data.RepositoryImpl
import com.nevidimka655.astracrypt.auth.data.TinkRepositoryImpl
import com.nevidimka655.astracrypt.auth.data.datastore.AuthDataStoreManager
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.TinkRepository
import com.nevidimka655.astracrypt.utils.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.crypto.tink.core.GetGlobalAssociatedDataPrf
import io.gromif.crypto.tink.data.AssociatedDataManager
import io.gromif.crypto.tink.data.KeysetManager

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