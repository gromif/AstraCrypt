package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.files.domain.service.ClockService
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.gromif.astracrypt.files.domain.validation.validator.NameValidator
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ImportUseCase(
    private val getAeadInfoUseCase: GetAeadInfoUseCase,
    private val clockService: ClockService,
    private val itemWriter: ItemWriter,
    private val fileUtilFactory: FileUtil.Factory,
    private val previewUtil: PreviewUtil,
    private val flagsUtil: FlagsUtil,
) {

    suspend operator fun invoke(
        pathList: List<String>,
        parentId: Long,
        saveSource: Boolean,
    ) = supervisorScope {
        val aeadInfo = getAeadInfoUseCase()
        pathList.forEach {
            launch {
                try {
                    processFile(aeadInfo, it, parentId, saveSource)
                } catch (_: Exception) {}
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
        val name = fileUtil.getName() ?: throw ValidationException
            .InvalidNameException("Name can't be null!")
        NameValidator(name)

        val type = fileUtil.parseType()
        val creationTime = fileUtil.creationTime().let {
            if (it == 0L) clockService.getCurrentTime() else it
        }
        val size = fileUtil.length() ?: 0
        require(size > -1) { throw ValidationException.InvalidFileSizeException() }

        val filePath = fileUtil.write() ?: throw ValidationException
            .InvalidPathException("File path can't be null!")
        val previewFilePath = previewUtil.getPreviewPath(type, path)
        val flags = flagsUtil.getFlags(type, path)

        if (!saveSource) fileUtil.delete()
        val importItemDto = ImportItemDto(
            parent = parentId,
            name = name,
            itemState = ItemState.Default,
            itemType = type,
            file = filePath,
            preview = previewFilePath,
            flags = flags,
            creationTime = creationTime,
            size = size
        )
        itemWriter.insert(aeadInfo = aeadInfo, importItemDto = importItemDto)
    }
}
