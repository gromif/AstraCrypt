package com.nevidimka655.astracrypt.utils

import android.content.Context
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.crypto.tink.KeysetFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetupManager @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val keysetFactory: KeysetFactory,
    private val repository: Repository,
    private val io: Io
) {

    fun isDatabaseCreated() = io.dataDir.exists()

    suspend fun setup() = withContext(Dispatchers.IO) {
        io.dataDir.mkdir()
        keysetFactory.associatedData
        arrayOf(R.string.music, R.string.document, R.string.video, R.string.photo).forEach {
            repository.newDirectory(
                directoryName = context.getString(it),
                parentDirectoryId = 0
            )
        }
    }


}