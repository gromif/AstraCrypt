package io.gromif.astracrypt.files.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.repository.item.DefaultItemDeleter
import io.gromif.astracrypt.files.data.repository.item.DefaultItemReader
import io.gromif.astracrypt.files.data.repository.item.DefaultItemWriter
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.repository.item.ItemDeleter
import io.gromif.astracrypt.files.domain.repository.item.ItemReader
import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.utils.Mapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ItemModule {

    @Singleton
    @Provides
    fun provideItemReader(
        daoManager: DaoManager,
        itemMapper: Mapper<FilesEntity, Item>,
        itemDetailsMapper: Mapper<DetailsTuple, ItemDetails>,
    ): ItemReader = DefaultItemReader(
        daoManager = daoManager,
        itemMapper = itemMapper,
        itemDetailsMapper = itemDetailsMapper
    )

    @Singleton
    @Provides
    fun provideItemWriter(daoManager: DaoManager): ItemWriter =
        DefaultItemWriter(daoManager = daoManager)

    @Singleton
    @Provides
    fun provideItemDeleter(
        daoManager: DaoManager,
        fileHandler: FileHandler
    ): ItemDeleter = DefaultItemDeleter(
        daoManager = daoManager,
        fileHandler = fileHandler
    )
}
