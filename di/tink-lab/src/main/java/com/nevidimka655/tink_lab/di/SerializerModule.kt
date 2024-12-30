package com.nevidimka655.tink_lab.di

import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.crypto.tink.core.encoders.HexUtil
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.util.KeySerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object SerializerModule {

    @Provides
    fun provideKeySerializer(hexUtil: HexUtil): Serializer<KeyDto, String> =
        KeySerializer(hexUtil = hexUtil)

}