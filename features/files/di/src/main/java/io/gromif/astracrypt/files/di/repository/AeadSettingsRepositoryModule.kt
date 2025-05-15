package io.gromif.astracrypt.files.di.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.dto.AeadInfoDto
import io.gromif.astracrypt.files.data.repository.DefaultAeadSettingsRepository
import io.gromif.astracrypt.files.data.util.mapper.AeadInfoDtoMapper
import io.gromif.astracrypt.files.data.util.mapper.AeadInfoMapper
import io.gromif.astracrypt.files.di.FilesDataStore
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.AeadSettingsRepository
import io.gromif.astracrypt.utils.Mapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AeadSettingsRepositoryModule {

    @Singleton
    @Provides
    fun provideAeadSettingsRepository(
        @FilesDataStore
        dataStore: DataStore<Preferences>,
        aeadInfoMapper: Mapper<AeadInfoDto, AeadInfo>,
        aeadInfoDtoMapper: Mapper<AeadInfo, AeadInfoDto>
    ): AeadSettingsRepository = DefaultAeadSettingsRepository(
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
