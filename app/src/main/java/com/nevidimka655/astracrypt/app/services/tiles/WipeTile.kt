package com.nevidimka655.astracrypt.app.services.tiles

import android.os.Build
import androidx.annotation.RequiresApi
import com.nevidimka655.astracrypt.core.di.IoDispatcher
import com.nevidimka655.astracrypt.data.db.AppDatabase
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.crypto.tink.data.AssociatedDataManager
import com.nevidimka655.crypto.tink.data.KeysetManager
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
    @Inject lateinit var filesUtil: FilesUtil
    @Inject lateinit var associatedDataManager: AssociatedDataManager

    override fun onClick() {
        super.onClick()
        launch(defaultDispatcher) {
            associatedDataManager.erase()

            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val aliases = keyStore.aliases()
            while (aliases.hasMoreElements()) {
                val alias = aliases.nextElement()
                keyStore.deleteEntry(alias)
            }

            database.clearAllTables()

            with(filesUtil) {
                dataDir.deleteRecursively()
                cacheDir.deleteRecursively()
            }
        }
    }
}