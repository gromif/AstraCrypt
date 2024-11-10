package com.nevidimka655.astracrypt.utils

import android.content.Context
import androidx.work.WorkManager
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.request.CachePolicy
import coil.transition.CrossfadeTransition
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsKeys
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsManager
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.haptic.Haptic
import java.io.File

/**
 * Contains singleton dependencies.
 */
object Engine {
    lateinit var appContext: Context

    val workManager get() = WorkManager.getInstance(appContext)
    lateinit var imageLoader: ImageLoader

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(context: Context) {
        Haptic.init(context = context)
        if (!::appContext.isInitialized) {
            appContext = context
        }
        if (!::imageLoader.isInitialized) imageLoader = ImageLoader.Builder(appContext)
            .components {
                add(TinkCoilFetcherFactory())
                add(VideoFrameDecoder.Factory())
            }
            .transitionFactory(CrossfadeTransition.Factory())
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
        KeysetFactory.Config.run {
            if (dataFileName.isEmpty()) {
                dataFileName = "grapefruit.ss0"
                dataFile = File("${IO.dataDir}/$dataFileName")
                dataLength = AppConfig.CRYPTO_NONCE_SIZE
                dataPasswordHashLength = AppConfig.AUTH_PASSWORD_HASH_LENGTH
                prefsFileNameDefault = PrefsKeys.FileNames.MASTER
                prefs = PrefsManager.clear
                prefsUniqueSaltFieldKey = PrefsKeys.ENCRYPTION_UNIQUE_SALT
            }
        }
    }

}