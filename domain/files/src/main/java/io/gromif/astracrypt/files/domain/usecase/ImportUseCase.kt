package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class ImportUseCase(
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
    private val fileUtil: FileUtil,
    private val previewUtil: PreviewUtil,
    private val flagsUtil: FlagsUtil
) {

    suspend operator fun invoke(
        pathList: List<String>,
        parentId: Long,
        saveSource: Boolean
    ) = coroutineScope {
        val aeadInfo = settingsRepository.getAeadInfo()
        pathList.chunked(4).forEach { chunk ->
            chunk.map { path ->
                launch {
                    processFile(aeadInfo, path, parentId, saveSource)
                }
            }.joinAll()
        }
    }

    private suspend fun processFile(
        aeadInfo: AeadInfo,
        path: String,
        parentId: Long,
        saveSource: Boolean
    ) = coroutineScope {
        ensureActive()
        fileUtil.open(path)
        val name = fileUtil.getName() ?: return@coroutineScope
        val size = fileUtil.length() ?: return@coroutineScope
        val creationTime = fileUtil.creationTime()
        val type = fileUtil.parseType()
        ensureActive()
        val flags = flagsUtil.getFlags(type, path)
        ensureActive()
        val filePath = fileUtil.write()
        filePath ?: return@coroutineScope
        val previewFilePath = previewUtil.getPreviewPath(
            type = type,
            path = path
        )
        if (!saveSource) fileUtil.delete()
        repository.insert(
            parent = parentId,
            name = name,
            fileState = FileState.Default,
            fileType = type,
            file = filePath,
            fileAead = aeadInfo.fileAeadIndex,
            preview = previewFilePath,
            previewAead = aeadInfo.previewAeadIndex,
            flags = flags,
            creationTime = creationTime,
            size = size
        )
    }

}