package io.gromif.astracrypt.files.files.util

import io.gromif.astracrypt.files.domain.usecase.navigator.CloseNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetNavBackStackFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.OpenNavFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.ResetNavBackStackUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.SwapNavBackStackUseCase
import javax.inject.Inject

internal data class NavigatorUseCases @Inject constructor(
    val getNavBackStackFlowUseCase: GetNavBackStackFlowUseCase,
    val getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase,
    val openNavFolderUseCase: OpenNavFolderUseCase,
    val closeNavFolderUseCase: CloseNavFolderUseCase,
    val resetNavBackStackUseCase: ResetNavBackStackUseCase,
    val swapNavBackStackUseCase: SwapNavBackStackUseCase
)
