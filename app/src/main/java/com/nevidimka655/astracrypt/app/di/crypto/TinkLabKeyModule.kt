package com.nevidimka655.astracrypt.app.di.crypto

import com.nevidimka655.crypto.tink.domain.usecase.SerializeKeysetByKeyUseCase
import com.nevidimka655.crypto.tink.domain.usecase.encoder.HexUseCase
import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha256UseCase
import com.nevidimka655.tink_lab.domain.TinkLabKeyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TinkLabKeyModule {

    @Provides
    fun provideTinkLabKeyManager(
        serializeKeysetByKeyUseCase: SerializeKeysetByKeyUseCase,
        sha256UseCase: Sha256UseCase,
        hexUseCase: HexUseCase
    ) = TinkLabKeyManager(
        serializeKeysetByKeyUseCase = serializeKeysetByKeyUseCase,
        sha256UseCase = sha256UseCase,
        hexUseCase = hexUseCase
    )

}