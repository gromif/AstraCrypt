package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive

class ImportUseCase(
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
    private val fileUtil: FileUtil,
    private val previewUtil: PreviewUtil,
    private val flagsUtil: FlagsUtil,
) {

    suspend operator fun invoke(
        pathList: List<String>,
        parentId: Long,
        saveSource: Boolean,
    ) = coroutineScope {
        val aeadInfo = settingsRepository.getAeadInfo()
        pathList.forEach {
            ensureActive()
            processFile(aeadInfo, it, parentId, saveSource)
        }
    }

    private suspend fun processFile(
        aeadInfo: AeadInfo,
        path: String,
        parentId: Long,
        saveSource: Boolean,
    ) = coroutineScope {
        fileUtil.open(path)
        val name = fileUtil.getName() ?: return@coroutineScope
        val type = fileUtil.parseType()

        val filePath = async {
            fileUtil.write().also {
                if (!saveSource) fileUtil.delete()
            }
        }
        val previewFilePath = async { previewUtil.getPreviewPath(type, path) }
        val flags = async { flagsUtil.getFlags(type, path) }

        val size = async { fileUtil.length() ?: 0 }
        val creationTime = async { fileUtil.creationTime() }

        repository.insert(
            parent = parentId,
            name = name,
            fileState = FileState.Default,
            fileType = type,
            file = filePath.await() ?: return@coroutineScope,
            fileAead = aeadInfo.fileAeadIndex,
            preview = previewFilePath.await(),
            previewAead = aeadInfo.previewAeadIndex,
            flags = flags.await(),
            creationTime = creationTime.await(),
            size = size.await()
        )
    }

}