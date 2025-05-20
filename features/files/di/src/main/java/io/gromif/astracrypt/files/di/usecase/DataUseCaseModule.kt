package io.gromif.astracrypt.files.di.usecase

import androidx.paging.PagingData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.di.DataSources
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.DataSource
import io.gromif.astracrypt.files.domain.usecase.data.GetDataFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.search.GetSearchRequestFlow

@Module
@InstallIn(ViewModelComponent::class)
internal object DataUseCaseModule {

    @DataSources.Default
    @ViewModelScoped
    @Provides
    fun provideGetFilesDataFlow(
        getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
        getSearchRequestFlow: GetSearchRequestFlow,
        @DataSources.Default dataSource: DataSource<PagingData<Item>>
    ) = GetDataFlowUseCase(
        getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCase,
        getSearchRequestFlow = getSearchRequestFlow,
        dataSource = dataSource
    )

    @DataSources.Starred
    @ViewModelScoped
    @Provides
    fun provideGetStarredDataFlow(
        getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
        getSearchRequestFlow: GetSearchRequestFlow,
        @DataSources.Starred dataSource: DataSource<PagingData<Item>>
    ) = GetDataFlowUseCase(
        getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCase,
        getSearchRequestFlow = getSearchRequestFlow,
        dataSource = dataSource
    )

}