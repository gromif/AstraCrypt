package io.gromif.astracrypt.files.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.data.repository.search.DefaultSearchManager
import io.gromif.astracrypt.files.data.repository.search.DefaultSearchStrategy
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.search.SearchManager
import io.gromif.astracrypt.files.domain.repository.search.SearchStrategy

@Module
@InstallIn(ViewModelComponent::class)
internal object SearchModule {

    @ViewModelScoped
    @Provides
    fun provideDefaultSearchManager(): SearchManager = DefaultSearchManager()

    @ViewModelScoped
    @Provides
    fun provideDefaultSearchStrategy(repository: Repository): SearchStrategy<Long, List<Long>> =
        DefaultSearchStrategy(repository = repository)

}
