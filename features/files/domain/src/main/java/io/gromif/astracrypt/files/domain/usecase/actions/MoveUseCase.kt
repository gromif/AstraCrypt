package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException

class MoveUseCase(
    private val getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase,
    private val repository: Repository
) {

    suspend operator fun invoke(ids: List<Long>) {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        val folderId = getCurrentNavFolderUseCase().id

        repository.move(
            ids = ids,
            parentId = folderId
        )
    }
}
