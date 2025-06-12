package io.gromif.astracrypt.files.files.util

import io.gromif.astracrypt.files.domain.usecase.actions.CreateFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.DeleteUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.SetStateUseCase
import javax.inject.Inject

internal data class ActionUseCases @Inject constructor(
    val createFolderUseCase: CreateFolderUseCase,
    val deleteUseCase: DeleteUseCase,
    val moveUseCase: MoveUseCase,
    val renameUseCase: RenameUseCase,
    val setStateUseCase: SetStateUseCase,
)
