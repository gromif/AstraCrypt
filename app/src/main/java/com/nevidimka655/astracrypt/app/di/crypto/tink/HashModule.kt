package com.nevidimka655.astracrypt.app.di.crypto.tink

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.core.hash.Sha256Util
import io.gromif.crypto.tink.core.hash.Sha384Util

@Module
@InstallIn(SingletonComponent::class)
object HashModule {
    @Provides
    fun provideSha256Service() = Sha256Util()

    @Provides
    fun provideSha384Service() = Sha384Util()
}