package io.gromif.astracrypt.files.di

import android.net.Uri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesDaoAeadAdapter
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.repository.RepositoryImpl
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.data.util.ExportUtil
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.utils.Mapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        filesDao: FilesDao,
        aeadHandler: AeadHandler,
        fileHandler: FileHandler,
        exportUtil: ExportUtil,
        itemMapper: Mapper<FilesEntity, Item>,
        itemDetailsMapper: Mapper<DetailsTuple, ItemDetails>,
        uriMapper: Mapper<String, Uri>,
    ): Repository = RepositoryImpl(
        filesDao = filesDao,
        filesDaoAeadAdapterFactory = FilesDaoAeadAdapter.Factory(
            filesDao = filesDao,
            aeadHandler = aeadHandler
        ),
        fileHandler = fileHandler,
        itemMapper = itemMapper,
        exportUtil = exportUtil,
        itemDetailsMapper = itemDetailsMapper,
        uriMapper = uriMapper
    )

}