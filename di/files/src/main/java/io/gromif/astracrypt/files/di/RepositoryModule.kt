package io.gromif.astracrypt.files.di

import com.nevidimka655.astracrypt.utils.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.repository.RepositoryImpl
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.util.AeadUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        filesDao: FilesDao,
        aeadUtil: AeadUtil,
        settingsRepository: SettingsRepository,
        fileItemMapper: Mapper<FilesEntity, FileItem>,
    ): Repository = RepositoryImpl(
        filesDao = filesDao,
        aeadUtil = aeadUtil,
        settingsRepository = settingsRepository,
        fileItemMapper = fileItemMapper,
    )

}