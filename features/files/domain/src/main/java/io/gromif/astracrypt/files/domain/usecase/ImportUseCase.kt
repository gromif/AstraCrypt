package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ImportUseCase(
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
    private val fileUtilFactory: FileUtil.Factory,
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
            launch {
                processFile(aeadInfo, it, parentId, saveSource)
            }
        }
    }

    private suspend fun processFile(
        aeadInfo: AeadInfo,
        path: String,
        parentId: Long,
        saveSource: Boolean,
    ) = coroutineScope {
        val fileUtil = fileUtilFactory.create()
        fileUtil.open(path)
        val name = fileUtil.getName() ?: return@coroutineScope
        val type = fileUtil.parseType()
        val creationTime = fileUtil.creationTime()
        val size = fileUtil.length() ?: 0

        val filePath = fileUtil.write() ?: return@coroutineScope
        val previewFilePath = previewUtil.getPreviewPath(type, path)
        val flags = flagsUtil.getFlags(type, path)

        if (!saveSource) fileUtil.delete()
        repository.insert(
            parent = parentId,
            name = name,
            fileState = FileState.Default,
            itemType = type,
            file = filePath ,
            fileAead = aeadInfo.fileAeadIndex,
            preview = previewFilePath,
            previewAead = aeadInfo.previewAeadIndex,
            flags = flags,
            creationTime = creationTime,
            size = size
        )
    }

}