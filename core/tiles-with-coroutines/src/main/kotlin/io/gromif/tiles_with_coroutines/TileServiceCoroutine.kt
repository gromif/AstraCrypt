package io.gromif.tiles_with_coroutines

import android.annotation.SuppressLint
import android.service.quicksettings.TileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

@SuppressLint("NewApi")
open class TileServiceCoroutine : TileService(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + SupervisorJob(job)

    override fun onCreate() {
        super.onCreate()
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}