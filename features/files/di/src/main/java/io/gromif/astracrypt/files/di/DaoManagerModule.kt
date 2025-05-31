package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.ExportTuple
import io.gromif.astracrypt.files.data.db.tuples.RenameTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoManagerModule {

    @Singleton
    @Provides
    fun provideDaoManager(
        filesDao: FilesDao,
        filesEntityAeadHandler: AbstractAeadHandler<FilesEntity>,
        deleteTupleAeadHandler: AbstractAeadHandler<DeleteTuple>,
        detailsTupleAeadHandler: AbstractAeadHandler<DetailsTuple>,
        exportTupleAeadHandler: AbstractAeadHandler<ExportTuple>,
        renameTupleAeadHandler: AbstractAeadHandler<RenameTuple>,
        updateTupleAeadHandler: AbstractAeadHandler<UpdateAeadTuple>,
    ) = DaoManager(
        filesDao = filesDao,
        filesEntityAeadHandler = filesEntityAeadHandler,
        deleteTupleAeadHandler = deleteTupleAeadHandler,
        detailsTupleAeadHandler = detailsTupleAeadHandler,
        exportTupleAeadHandler = exportTupleAeadHandler,
        renameTupleAeadHandler = renameTupleAeadHandler,
        updateTupleAeadHandler = updateTupleAeadHandler,
    )

}
