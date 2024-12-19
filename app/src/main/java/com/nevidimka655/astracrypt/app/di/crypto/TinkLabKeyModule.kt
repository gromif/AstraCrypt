package com.nevidimka655.astracrypt.app.di.crypto

import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByKeyService
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
        serializeKeysetByKeyService: SerializeKeysetByKeyService,
        sha256Service: Sha256Service,
        hexService: HexService
    ) = TinkLabKeyManager(
        serializeKeysetByKeyService = serializeKeysetByKeyService,
        sha256Service = sha256Service,
        hexService = hexService
    )

}