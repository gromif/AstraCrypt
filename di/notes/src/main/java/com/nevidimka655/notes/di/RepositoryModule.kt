package com.nevidimka655.notes.di

import com.nevidimka655.domain.notes.paging.PagingProvider
import com.nevidimka655.domain.notes.repository.Repository
import com.nevidimka655.notes.data.database.NotesDao
import com.nevidimka655.notes.data.mappers.DataToDomainMapper
import com.nevidimka655.notes.data.paging.PagingProviderImpl
import com.nevidimka655.notes.data.repository.RepositoryImpl
import com.nevidimka655.notes.data.repository.RepositoryProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @Provides
    fun provideRepositoryProvider(
        repositoryImpl: Repository,
        //settingsDataStoreManager: SettingsDataStoreManager
    ): RepositoryProviderImpl = RepositoryProviderImpl(
        repository = repositoryImpl,
        //aeadInfoFlow = settingsDataStoreManager.aeadInfoFlow
    )

    @Provides
    fun provideRepositoryImpl(
        dao: NotesDao,
        dataToDomainMapper: DataToDomainMapper
    ): Repository = RepositoryImpl(
        dao = dao,
        dataToDomainMapper = dataToDomainMapper
    )

    @Provides
    fun providePagingProvider(
        repositoryProviderImpl: RepositoryProviderImpl
    ): PagingProvider = PagingProviderImpl(
        repositoryProviderImpl = repositoryProviderImpl
    )

    @Provides
    fun provideDataToDomainMapper(): DataToDomainMapper = DataToDomainMapper()

}