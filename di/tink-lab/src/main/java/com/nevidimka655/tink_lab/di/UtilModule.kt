package com.nevidimka655.tink_lab.di

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service
import com.nevidimka655.crypto.tink.data.serializers.SerializeKeysetByKeyService
import com.nevidimka655.tink_lab.data.util.KeyFactory
import com.nevidimka655.tink_lab.domain.model.DataType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UtilModule {

    @Provides
    fun provideKeyFactory(
        serializeKeysetByKeyService: SerializeKeysetByKeyService,
        sha256Service: Sha256Service,
        hexService: HexService,
        dataTypeToIdMapper: Mapper<DataType, Int>
    ): KeyFactory = KeyFactory(
        serializeKeysetByKeyService = serializeKeysetByKeyService,
        sha256Service = sha256Service,
        hexService = hexService,
        dataTypeToIdMapper = dataTypeToIdMapper
    )

}