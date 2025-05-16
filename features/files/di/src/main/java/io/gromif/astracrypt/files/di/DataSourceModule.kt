package io.gromif.astracrypt.files.di

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.provider.DataSourceImpl
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.AeadSettingsRepository
import io.gromif.astracrypt.files.domain.repository.DataSource
import io.gromif.astracrypt.files.domain.repository.Repository

@Module
@InstallIn(ViewModelComponent::class)
internal object DataSourceModule {

    @ViewModelScoped
    @Provides
    fun provideDataSource(
        filesDao: FilesDao,
        aeadHandler: AeadHandler,
        repository: Repository,
        aeadSettingsRepository: AeadSettingsRepository
    ): DataSource<PagingData<Item>> = DataSourceImpl(
        filesDao = filesDao,
        pagingConfig = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        aeadHandler = aeadHandler,
        repository = repository,
        aeadSettingsRepository = aeadSettingsRepository
    )

}