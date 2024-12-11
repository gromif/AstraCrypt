package com.nevidimka655.astracrypt.app.extensions

import androidx.work.CoroutineWorker

val CoroutineWorker.contentResolver get() = applicationContext.contentResolver