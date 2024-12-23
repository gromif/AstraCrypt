package com.nevidimka655.astracrypt.app.utils

import android.content.Context
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.core.di.IoDispatcher
import com.nevidimka655.astracrypt.data.io.FilesService
import com.nevidimka655.astracrypt.data.repository.RepositoryProviderImpl
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FileSystemSetupManager @Inject constructor(
    @ApplicationContext
    private val context: Context,

    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,

    private val keysetManager: KeysetManager,
    private val repositoryProviderImpl: RepositoryProviderImpl,
    private val filesService: FilesService
) {
    fun isDatabaseCreated() = filesService.dataDir.exists()

    suspend fun setup() = withContext(defaultDispatcher) {
        filesService.dataDir.mkdir()
        keysetManager.associatedData
        val foldersArray = arrayOf(
            R.string.music, R.string.document, R.string.video, R.string.photo
        )
        repositoryProviderImpl.repository.first().let { repository ->
            foldersArray.forEach {
                repository.newDirectory(name = context.getString(it), parentId = 0)
            }
        }
    }


}