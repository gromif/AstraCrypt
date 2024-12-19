package com.nevidimka655.astracrypt.app.di.crypto.tink

import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha256UseCase
import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha384UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HashModule {
    @Provides
    fun provideSha256UseCase() = Sha256UseCase()

    @Provides
    fun provideSha384UseCase() = Sha384UseCase()
}