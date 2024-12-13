package com.nevidimka655.astracrypt.app.tiles

import android.os.Build
import androidx.annotation.RequiresApi
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.data.room.AppDatabase
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.secureRandom
import com.nevidimka655.tiles_with_coroutines.TileServiceCoroutine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.RandomAccessFile
import java.security.KeyStore
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.N)
@AndroidEntryPoint
class QuickDataDeletion @Inject constructor() : TileServiceCoroutine() {
    @IoDispatcher
    @Inject lateinit var defaultDispatcher: CoroutineDispatcher
    @Inject lateinit var database: AppDatabase
    @Inject lateinit var io: Io
    @Inject lateinit var keysetFactory: KeysetFactory

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

            with(io) {
                dataDir.deleteRecursively()
                cacheDir.deleteRecursively()
            }

            database.clearAllTables()

            RandomAccessFile(keysetFactory.dataFile, "rws").use {
                val byteArray = ByteArray(96)
                val secureSeed = "${System.currentTimeMillis()}AC_$classLoader".toByteArray()
                secureRandom(secureSeed).nextBytes(byteArray)
                it.write(byteArray)
            }
        }
    }
}