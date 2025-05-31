package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.util.AeadHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoManagerModule {

    @Singleton
    @Provides
    fun provideDaoManager(
        filesDao: FilesDao,
        aeadHandler: AeadHandler
    ) = DaoManager(
        filesDao = filesDao,
        aeadHandler = aeadHandler
    )

}
