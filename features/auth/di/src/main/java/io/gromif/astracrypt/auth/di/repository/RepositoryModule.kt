package io.gromif.astracrypt.auth.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.auth.data.repository.RepositoryImpl
import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.service.TinkService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        tinkService: TinkService
    ): Repository = RepositoryImpl(
        tinkService = tinkService
    )

}
