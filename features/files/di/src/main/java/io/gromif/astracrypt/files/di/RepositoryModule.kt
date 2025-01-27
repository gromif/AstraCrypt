package io.gromif.astracrypt.files.di

import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.repository.RepositoryImpl
import io.gromif.astracrypt.files.data.util.ExportUtil
import io.gromif.astracrypt.files.data.util.FileHandler
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
        fileHandler: FileHandler,
        exportUtil: ExportUtil,
        fileItemMapper: Mapper<FilesEntity, FileItem>,
        uriMapper: Mapper<String, Uri>,
    ): Repository = RepositoryImpl(
        filesDao = filesDao,
        aeadUtil = aeadUtil,
        settingsRepository = settingsRepository,
        fileHandler = fileHandler,
        fileItemMapper = fileItemMapper,
        exportUtil = exportUtil,
        uriMapper = uriMapper
    )

}