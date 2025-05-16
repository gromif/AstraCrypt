package io.gromif.astracrypt.files.di.usecase

import androidx.paging.PagingData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.DataSource
import io.gromif.astracrypt.files.domain.usecase.data.GetFilesDataFlow
import io.gromif.astracrypt.files.domain.usecase.data.GetStarredDataFlow
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase
import io.gromif.astracrypt.files.domain.usecase.data.SetDataSearchUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object DataUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetFilesDataFlow(dataSource: DataSource<PagingData<Item>>) =
        GetFilesDataFlow(dataSource = dataSource)

    @ViewModelScoped
    @Provides
    fun provideGetStarredDataFlow(dataSource: DataSource<PagingData<Item>>) =
        GetStarredDataFlow(dataSource = dataSource)

    @ViewModelScoped
    @Provides
    fun provideInvalidateDataSourceUseCase(dataSource: DataSource<PagingData<Item>>) =
        InvalidateDataSourceUseCase(dataSource = dataSource)

    @ViewModelScoped
    @Provides
    fun provideSetDataSearchUseCase(dataSource: DataSource<PagingData<Item>>) =
        SetDataSearchUseCase(dataSource = dataSource)

}