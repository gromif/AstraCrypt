package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.CloseNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetNavBackStackFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.OpenNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.ResetNavBackStackUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.SwapNavBackStackUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object NavigatorUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideCloseNavFolderUseCase(
        storageNavigator: StorageNavigator
    ) = CloseNavFolderUseCase(storageNavigator = storageNavigator)

    @ViewModelScoped
    @Provides
    fun provideGetCurrentNavFolderUseCase(storageNavigator: StorageNavigator) =
        GetCurrentNavFolderUseCase(storageNavigator = storageNavigator)

    @ViewModelScoped
    @Provides
    fun provideGetCurrentNavFolderFlowUseCase(storageNavigator: StorageNavigator) =
        GetCurrentNavFolderFlowUseCase(storageNavigator = storageNavigator)

    @ViewModelScoped
    @Provides
    fun provideGetNavBackStackFlowUseCase(storageNavigator: StorageNavigator) =
        GetNavBackStackFlowUseCase(storageNavigator = storageNavigator)

    @ViewModelScoped
    @Provides
    fun provideOpenNavFolderUseCase(
        storageNavigator: StorageNavigator,
        getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase,
        getValidationRulesUseCase: GetValidationRulesUseCase
    ) = OpenNavFolderUseCase(
        storageNavigator = storageNavigator,
        getCurrentNavFolderUseCase = getCurrentNavFolderUseCase,
        getValidationRulesUseCase = getValidationRulesUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideResetNavBackStackUseCase(swapNavBackStackUseCase: SwapNavBackStackUseCase) =
        ResetNavBackStackUseCase(swapNavBackStackUseCase = swapNavBackStackUseCase)

    @ViewModelScoped
    @Provides
    fun provideSwapNavBackStackUseCase(
        storageNavigator: StorageNavigator
    ) = SwapNavBackStackUseCase(storageNavigator = storageNavigator)

}
