package com.nevidimka655.astracrypt.tabs.settings.security.quick_actions.tiles

import android.os.Build
import androidx.annotation.RequiresApi
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.IO
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.secureRandom
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsManager
import com.nevidimka655.tiles_with_coroutines.TileServiceCoroutine
import kotlinx.coroutines.*
import java.io.RandomAccessFile
import java.security.KeyStore

@RequiresApi(Build.VERSION_CODES.N)
class QuickDataDeletion: TileServiceCoroutine() {
    override fun onClick() {
        super.onClick()
        Engine.init(applicationContext)
        launch(Dispatchers.IO) {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val aliases = keyStore.aliases()
            while (aliases.hasMoreElements()) {
                val alias = aliases.nextElement()
                keyStore.deleteEntry(alias)
            }

            with(IO) {
                dataDir.deleteRecursively()
                cacheDir.deleteRecursively()
            }

            Repository.clearAllTables()
            PrefsManager.clearAllPrefs()

            RandomAccessFile(KeysetFactory.dataFile, "rws").use {
                val byteArray = ByteArray(96)
                val secureSeed = "${System.currentTimeMillis()}AC_$classLoader".toByteArray()
                secureRandom(secureSeed).nextBytes(byteArray)
                it.write(byteArray)
            }
        }
    }
}