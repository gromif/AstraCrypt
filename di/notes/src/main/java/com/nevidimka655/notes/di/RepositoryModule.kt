package com.nevidimka655.notes.di

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.nevidimka655.astracrypt.notes.db.NotesDao
import com.nevidimka655.domain.notes.paging.PagingProvider
import com.nevidimka655.domain.notes.repository.Repository
import com.nevidimka655.domain.notes.repository.SettingsRepository
import com.nevidimka655.notes.data.mappers.DataToDomainMapper
import com.nevidimka655.notes.data.paging.PagingProviderImpl
import com.nevidimka655.notes.data.repository.RepositoryImpl
import com.nevidimka655.notes.data.repository.RepositoryProviderImpl
import com.nevidimka655.notes.data.repository.SettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

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
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): SettingsRepository = SettingsRepositoryImpl(
        dataStore = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile("notes") }
        )
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