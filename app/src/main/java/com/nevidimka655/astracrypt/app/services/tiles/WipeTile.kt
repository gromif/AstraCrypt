package com.nevidimka655.astracrypt.app.services.tiles

import android.os.Build
import androidx.annotation.RequiresApi
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.data.database.AppDatabase
import com.nevidimka655.astracrypt.data.io.FilesService
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.extensions.secureRandom
import com.nevidimka655.tiles_with_coroutines.TileServiceCoroutine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.RandomAccessFile
import java.security.KeyStore
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.N)
@AndroidEntryPoint
class WipeTile @Inject constructor() : TileServiceCoroutine() {
    @IoDispatcher
    @Inject lateinit var defaultDispatcher: CoroutineDispatcher
    @Inject lateinit var database: AppDatabase
    @Inject lateinit var filesService: FilesService
    @Inject lateinit var keysetManager: KeysetManager

    override fun onClick() {
        super.onClick()
        launch(defaultDispatcher) {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val aliases = keyStore.aliases()
            while (aliases.hasMoreElements()) {
                val alias = aliases.nextElement()
                keyStore.deleteEntry(alias)
            }

            RandomAccessFile(keysetManager.dataFile, "rws").use {
                it.write(ByteArray(96))
            }

            with(filesService) {
                dataDir.deleteRecursively()
                cacheDir.deleteRecursively()
            }

            database.clearAllTables()
        }
    }
}