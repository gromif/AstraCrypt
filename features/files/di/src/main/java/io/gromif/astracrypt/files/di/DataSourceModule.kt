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
import io.gromif.astracrypt.files.data.repository.dataSource.StarredDataSource
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.DataSource
import io.gromif.astracrypt.files.domain.repository.search.SearchStrategy
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
internal object DataSourceModule {

    @DataSources.Default
    @ViewModelScoped
    @Provides
    fun provideDefaultDataSource(
        defaultSearchStrategy: SearchStrategy<Long, List<Long>>,
        filesDao: FilesDao,
        aeadHandler: AeadHandler
    ): DataSource<PagingData<Item>> = DataSourceImpl(
        defaultSearchStrategy = defaultSearchStrategy,
        filesDao = filesDao,
        pagingConfig = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        aeadHandler = aeadHandler
    )

    @DataSources.Starred
    @ViewModelScoped
    @Provides
    fun provideStarredDataSource(
        filesDao: FilesDao,
        aeadHandler: AeadHandler
    ): DataSource<PagingData<Item>> = StarredDataSource(
        filesDao = filesDao,
        pagingConfig = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        aeadHandler = aeadHandler
    )

}

object DataSources {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Default

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Starred
}