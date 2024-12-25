package com.nevidimka655.astracrypt.app.di.crypto.tink

import com.nevidimka655.crypto.tink.core.encoders.HexUtil
import com.nevidimka655.crypto.tink.core.hash.Sha384Util
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializer
import com.nevidimka655.crypto.tink.data.keyset.KeysetAeadFactory
import com.nevidimka655.crypto.tink.data.keyset.KeysetKeyFactory
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializerWithAead
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializerWithKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializeKeysetModule {

    @Singleton
    @Provides
    fun provideSerializeKeysetByKeyService(
        keysetSerializerWithAead: KeysetSerializerWithAead,
        keysetAeadFactory: KeysetAeadFactory
    ) = KeysetSerializerWithKey(
        keysetSerializerWithAead = keysetSerializerWithAead,
        keysetAeadFactory = keysetAeadFactory
    )

    @Singleton
    @Provides
    fun provideSerializeKeysetByAeadService(
        hexUtil: HexUtil
    ) = KeysetSerializerWithAead(hexUtil = hexUtil)

    @Singleton
    @Provides
    fun provideKeysetSerializer(
        hexUtil: HexUtil
    ) = KeysetSerializer(hexUtil = hexUtil)

    @Singleton
    @Provides
    fun provideKeysetAeadFactory(keysetKeyFactory: KeysetKeyFactory) =
        KeysetAeadFactory(keysetKeyFactory = keysetKeyFactory)

    @Singleton
    @Provides
    fun provideKeysetKeyFactory(sha384Util: Sha384Util) =
        KeysetKeyFactory(sha384Util = sha384Util)
}