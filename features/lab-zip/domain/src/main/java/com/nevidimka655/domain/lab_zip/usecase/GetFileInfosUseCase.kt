package com.nevidimka655.domain.lab_zip.usecase

import com.nevidimka655.domain.lab_zip.FileInfo
import com.nevidimka655.domain.lab_zip.Repository

class GetFileInfosUseCase(
    private val repository: Repository
) {

    operator fun invoke(paths: List<String>): List<FileInfo> {
        return repository.getFilesInfo(paths = paths)
    }

}