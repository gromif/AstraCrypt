package io.gromif.lab_zip.domain.usecase

import io.gromif.lab_zip.domain.FileInfo
import io.gromif.lab_zip.domain.Repository

class GetSourceFileInfoUseCase(
    private val repository: Repository
) {

    operator fun invoke(path: String): FileInfo {
        return repository.getSourceFileInfo(path = path)
    }

}