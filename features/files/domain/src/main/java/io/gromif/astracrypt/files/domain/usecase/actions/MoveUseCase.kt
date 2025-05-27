package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import kotlinx.coroutines.flow.first

class MoveUseCase(
    private val getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
    private val itemWriter: ItemWriter
) {

    suspend operator fun invoke(ids: List<Long>) {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        val folderId = getCurrentNavFolderFlowUseCase().first().id

        itemWriter.move(
            ids = ids,
            parentId = folderId
        )
    }
}
