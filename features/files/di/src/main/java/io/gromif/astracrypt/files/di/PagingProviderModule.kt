package io.gromif.astracrypt.files.di

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.provider.PagingProviderImpl
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.provider.PagingProvider
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.util.AeadUtil

@Module
@InstallIn(ViewModelComponent::class)
internal object PagingProviderModule {

    @ViewModelScoped
    @Provides
    fun providePagingProvider(
        filesDao: FilesDao,
        aeadUtil: AeadUtil,
        repository: Repository,
        settingsRepository: SettingsRepository
    ): PagingProvider<PagingData<Item>> = PagingProviderImpl(
        filesDao = filesDao,
        pagingConfig = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        aeadUtil = aeadUtil,
        repository = repository,
        settingsRepository = settingsRepository
    )

}