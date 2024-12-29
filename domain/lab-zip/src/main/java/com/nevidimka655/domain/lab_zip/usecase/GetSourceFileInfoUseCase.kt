package com.nevidimka655.domain.lab_zip.usecase

import com.nevidimka655.domain.lab_zip.FileInfo
import com.nevidimka655.domain.lab_zip.Repository

class GetSourceFileInfoUseCase(
    private val repository: Repository
) {

    operator fun invoke(path: String): FileInfo {
        return repository.getSourceFileInfo(path = path)
    }

}