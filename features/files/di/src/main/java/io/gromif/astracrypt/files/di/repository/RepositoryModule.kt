package io.gromif.astracrypt.files.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.repository.RepositoryImpl
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
        daoManager: DaoManager,
        fileHandler: FileHandler,
        itemMapper: Mapper<FilesEntity, Item>,
        itemDetailsMapper: Mapper<DetailsTuple, ItemDetails>,
    ): Repository = RepositoryImpl(
        daoManager = daoManager,
        fileHandler = fileHandler,
        itemMapper = itemMapper,
        itemDetailsMapper = itemDetailsMapper,
    )

}