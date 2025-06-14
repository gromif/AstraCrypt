package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.domain.repository.search.SearchManager
import io.gromif.astracrypt.files.domain.usecase.search.GetSearchRequestFlow
import io.gromif.astracrypt.files.domain.usecase.search.RequestSearchUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object SearchUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetSearchRequestFlow(searchManager: SearchManager) =
        GetSearchRequestFlow(searchManager = searchManager)

    @ViewModelScoped
    @Provides
    fun provideRequestSearchUseCase(searchManager: SearchManager) =
        RequestSearchUseCase(searchManager = searchManager)
}
