package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import kotlinx.coroutines.flow.first

class OpenNavFolderUseCase(
    private val storageNavigator: StorageNavigator,
    private val getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
    private val getValidationRulesUseCase: GetValidationRulesUseCase,
) {

    suspend operator fun invoke(id: Long, name: String) {
        val currentFolderId = getCurrentNavFolderFlowUseCase().first().id
        if (id != currentFolderId) {
            val validationRules = getValidationRulesUseCase()
            val maxBackstackNameLength = validationRules.maxBackstackNameLength
            val targetName = if (name.length > maxBackstackNameLength) {
                "${name.take(maxBackstackNameLength)}…"
            } else {
                name
            }

            val newFolder = StorageNavigator.Folder(id, targetName)
            storageNavigator.push(folder = newFolder)
        }
    }
}
