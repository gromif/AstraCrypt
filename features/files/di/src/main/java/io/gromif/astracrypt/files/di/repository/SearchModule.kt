package io.gromif.astracrypt.files.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.data.repository.search.DefaultSearchManager
import io.gromif.astracrypt.files.domain.repository.search.SearchManager

@Module
@InstallIn(ViewModelComponent::class)
internal object SearchModule {

    @ViewModelScoped
    @Provides
    fun provideDefaultSearchManager(): SearchManager = DefaultSearchManager()

}
