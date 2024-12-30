package com.nevidimka655.tink_lab.di

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.mapper.DataTypeToIdMapper
import com.nevidimka655.tink_lab.data.mapper.DtoToKeyMapper
import com.nevidimka655.tink_lab.data.mapper.IdToDataTypeMapper
import com.nevidimka655.tink_lab.data.mapper.KeyToDtoMapper
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object MapperModule {

    @Provides
    fun provideDtoToKeyMapper(idToDataTypeMapper: Mapper<Int, DataType>): Mapper<KeyDto, Key> =
        DtoToKeyMapper(idToDataTypeMapper = idToDataTypeMapper)

    @Provides
    fun provideKeyToDtoMapper(dataTypeToIdMapper: Mapper<DataType, Int>): Mapper<Key, KeyDto> =
        KeyToDtoMapper(dataTypeToIdMapper = dataTypeToIdMapper)

    @Provides
    fun provideDataTypeToIdMapper(): Mapper<DataType, Int> = DataTypeToIdMapper()

    @Provides
    fun provideIdToDataTypeMapper(): Mapper<Int, DataType> = IdToDataTypeMapper()

}