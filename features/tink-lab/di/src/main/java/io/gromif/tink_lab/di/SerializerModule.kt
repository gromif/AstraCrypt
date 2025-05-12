package io.gromif.tink_lab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.astracrypt.utils.Serializer
import io.gromif.crypto.tink.core.encoders.HexEncoder
import io.gromif.tink_lab.data.dto.KeyDto
import io.gromif.tink_lab.data.util.KeySerializer

@Module
@InstallIn(ViewModelComponent::class)
internal object SerializerModule {

    @Provides
    fun provideKeySerializer(hexEncoder: HexEncoder): Serializer<KeyDto, String> =
        KeySerializer(hexEncoder = hexEncoder)

}