package com.nevidimka655.astracrypt.app.utils

import android.content.Context
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.data.repository.files.FilesRepositoryProvider
import com.nevidimka655.crypto.tink.KeysetFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetupManager @Inject constructor(
    @ApplicationContext
    private val context: Context,

    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,

    private val keysetFactory: KeysetFactory,
    private val filesRepositoryProvider: FilesRepositoryProvider,
    private val io: Io
) {
    fun isDatabaseCreated() = io.dataDir.exists()

    suspend fun setup() = withContext(defaultDispatcher) {
        io.dataDir.mkdir()
        keysetFactory.associatedData
        val foldersArray = arrayOf(
            R.string.music, R.string.document, R.string.video, R.string.photo
        )
        filesRepositoryProvider.filesRepository.first().let { repository ->
            foldersArray.forEach {
                repository.newDirectory(name = context.getString(it), parentId = 0)
            }
        }
    }


}