package io.gromif.astracrypt.files.di.usecase

import androidx.paging.PagingData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.CloseNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetNavBackStackFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.OpenNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.ResetNavBackStackUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.SwapNavBackStackUseCase

private typealias Type = PagingData<Item>

@Module
@InstallIn(ViewModelComponent::class)
internal object NavigatorUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideCloseNavFolderUseCase(
        storageNavigator: StorageNavigator,
        invalidateDataSourceUseCase: InvalidateDataSourceUseCase<Type>
    ) = CloseNavFolderUseCase<Type>(
        storageNavigator = storageNavigator,
        invalidateDataSourceUseCase = invalidateDataSourceUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideGetCurrentNavFolderUseCase(storageNavigator: StorageNavigator) =
        GetCurrentNavFolderUseCase(storageNavigator = storageNavigator)

    @ViewModelScoped
    @Provides
    fun provideGetNavBackStackFlowUseCase(storageNavigator: StorageNavigator) =
        GetNavBackStackFlowUseCase(storageNavigator = storageNavigator)

    @ViewModelScoped
    @Provides
    fun provideOpenNavFolderUseCase(
        storageNavigator: StorageNavigator,
        getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase,
        invalidateDataSourceUseCase: InvalidateDataSourceUseCase<Type>,
        getValidationRulesUseCase: GetValidationRulesUseCase
    ) = OpenNavFolderUseCase<Type>(
        storageNavigator = storageNavigator,
        getCurrentNavFolderUseCase = getCurrentNavFolderUseCase,
        invalidateDataSourceUseCase = invalidateDataSourceUseCase,
        getValidationRulesUseCase = getValidationRulesUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideResetNavBackStackUseCase(swapNavBackStackUseCase: SwapNavBackStackUseCase<Type>) =
        ResetNavBackStackUseCase<Type>(swapNavBackStackUseCase = swapNavBackStackUseCase)

    @ViewModelScoped
    @Provides
    fun provideSwapNavBackStackUseCase(
        storageNavigator: StorageNavigator,
        invalidateDataSourceUseCase: InvalidateDataSourceUseCase<Type>
    ) = SwapNavBackStackUseCase<Type>(
        storageNavigator = storageNavigator,
        invalidateDataSourceUseCase = invalidateDataSourceUseCase
    )

}
