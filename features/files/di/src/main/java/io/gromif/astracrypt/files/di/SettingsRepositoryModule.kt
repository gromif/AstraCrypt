package io.gromif.astracrypt.files.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.dto.AeadInfoDto
import io.gromif.astracrypt.files.data.repository.SettingsRepositoryImpl
import io.gromif.astracrypt.files.data.util.mapper.AeadInfoDtoMapper
import io.gromif.astracrypt.files.data.util.mapper.AeadInfoMapper
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.utils.Mapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SettingsRepositoryModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(
        @FilesDataStore
        dataStore: DataStore<Preferences>,
        aeadInfoMapper: Mapper<AeadInfoDto, AeadInfo>,
        aeadInfoDtoMapper: Mapper<AeadInfo, AeadInfoDto>
    ): SettingsRepository = SettingsRepositoryImpl(
        dataStore = dataStore,
        aeadInfoMapper = aeadInfoMapper,
        aeadInfoDtoMapper = aeadInfoDtoMapper
    )

    @Singleton
    @Provides
    fun provideAeadInfoDtoMapper(): Mapper<AeadInfo, AeadInfoDto> = AeadInfoDtoMapper()

    @Singleton
    @Provides
    fun provideAeadInfoMapper(): Mapper<AeadInfoDto, AeadInfo> = AeadInfoMapper()

}