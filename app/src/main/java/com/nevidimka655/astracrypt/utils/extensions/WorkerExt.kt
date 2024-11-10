package com.nevidimka655.astracrypt.utils.extensions

import androidx.work.CoroutineWorker

val CoroutineWorker.contentResolver get() = applicationContext.contentResolver