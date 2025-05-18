package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase

class OpenNavFolderUseCase<T>(
    private val storageNavigator: StorageNavigator,
    private val getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase,
    private val invalidateDataSourceUseCase: InvalidateDataSourceUseCase<T>,
    private val getValidationRulesUseCase: GetValidationRulesUseCase,
) {

    operator fun invoke(id: Long, name: String) {
        val currentFolderId = getCurrentNavFolderUseCase().id

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
            invalidateDataSourceUseCase()
        }
    }
}
