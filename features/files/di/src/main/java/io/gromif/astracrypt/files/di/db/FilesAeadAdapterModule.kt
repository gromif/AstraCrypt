package io.gromif.astracrypt.files.di.db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesDaoAeadAdapter
import io.gromif.astracrypt.files.data.util.AeadHandler

@Module
@InstallIn(SingletonComponent::class)
internal object FilesAeadAdapterModule {

    @Provides
    fun provideFactory(
        filesDao: FilesDao,
        aeadHandler: AeadHandler,
    ): FilesDaoAeadAdapter.Factory = FilesDaoAeadAdapter.Factory(
        filesDao = filesDao,
        aeadHandler = aeadHandler
    )

}