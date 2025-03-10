package io.gromif.astracrypt.profile.di

import android.net.Uri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.profile.data.repository.RepositoryImpl
import io.gromif.astracrypt.profile.data.util.ExternalIconUtil
import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.astracrypt.utils.Mapper

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

}