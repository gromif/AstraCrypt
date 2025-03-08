package io.gromif.lab_zip.domain.usecase

import io.gromif.lab_zip.domain.FileInfo
import io.gromif.lab_zip.domain.Repository

class GetFileInfosUseCase(
    private val repository: Repository
) {

    operator fun invoke(paths: List<String>): List<FileInfo> {
        return repository.getFilesInfo(paths = paths)
    }

}