package io.gromif.astracrypt.files.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesDaoAeadAdapter
import io.gromif.astracrypt.files.data.repository.DefaultAeadManager
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.domain.repository.AeadManager

@Module
@InstallIn(SingletonComponent::class)
internal object AeadManagerModule {

    @Provides
    fun provideAeadManager(
        filesDao: FilesDao,
        aeadHandler: AeadHandler,
        daoManager: DaoManager
    ): AeadManager = DefaultAeadManager(
        daoManager = daoManager,
        filesDaoAeadAdapterFactory = FilesDaoAeadAdapter.Factory(
            filesDao = filesDao,
            aeadHandler = aeadHandler
        ),
    )

}