package io.gromif.tink_lab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.astracrypt.utils.Mapper
import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tink_lab.data.dto.KeyDto
import io.gromif.tink_lab.data.mapper.DataTypeToIdMapper
import io.gromif.tink_lab.data.mapper.DtoToKeyMapper
import io.gromif.tink_lab.data.mapper.IdToDataTypeMapper
import io.gromif.tink_lab.data.mapper.KeyToDtoMapper

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