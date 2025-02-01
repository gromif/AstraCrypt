package io.gromif.astracrypt.files.di

import com.nevidimka655.astracrypt.utils.Serializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.data.serializer.ItemFlagsDtoSerializer

@Module
@InstallIn(SingletonComponent::class)
internal object SerializerModule {

    @Provides
    fun provideItemFlagsDtoSerializer(): Serializer<FileFlagsDto, String> = ItemFlagsDtoSerializer()

}