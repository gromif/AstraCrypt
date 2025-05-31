package io.gromif.astracrypt.files.di.util

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.ExportTuple
import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.data.db.tuples.RenameTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.data.util.AeadUtil
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.data.util.aead.handlers.DeleteTupleAeadHandler
import io.gromif.astracrypt.files.data.util.aead.handlers.DetailsTupleAeadHandler
import io.gromif.astracrypt.files.data.util.aead.handlers.ExportTupleAeadHandler
import io.gromif.astracrypt.files.data.util.aead.handlers.FilesEntityAeadHandler
import io.gromif.astracrypt.files.data.util.aead.handlers.PagerTupleAeadHandler
import io.gromif.astracrypt.files.data.util.aead.handlers.RenameTupleAeadHandler
import io.gromif.astracrypt.files.data.util.aead.handlers.UpdateTupleAeadHandler

@Module
@InstallIn(SingletonComponent::class)
internal object AeadHandlerModule {

    @Provides
    fun provideFilesEntityAeadHandler(
        aeadUtil: AeadUtil
    ): AbstractAeadHandler<FilesEntity> = FilesEntityAeadHandler(aeadUtil = aeadUtil)

    @Provides
    fun provideDetailsTupleAeadHandler(
        aeadUtil: AeadUtil
    ): AbstractAeadHandler<DetailsTuple> = DetailsTupleAeadHandler(aeadUtil = aeadUtil)

    @Provides
    fun provideDeleteTupleAeadHandler(
        aeadUtil: AeadUtil
    ): AbstractAeadHandler<DeleteTuple> = DeleteTupleAeadHandler(aeadUtil = aeadUtil)

    @Provides
    fun provideExportTupleAeadHandler(
        aeadUtil: AeadUtil
    ): AbstractAeadHandler<ExportTuple> = ExportTupleAeadHandler(aeadUtil = aeadUtil)

    @Provides
    fun providePagerTupleAeadHandler(
        aeadUtil: AeadUtil
    ): AbstractAeadHandler<PagerTuple> = PagerTupleAeadHandler(aeadUtil = aeadUtil)

    @Provides
    fun provideUpdateTupleAeadHandler(
        aeadUtil: AeadUtil
    ): AbstractAeadHandler<UpdateAeadTuple> = UpdateTupleAeadHandler(aeadUtil = aeadUtil)

    @Provides
    fun provideRenameTupleAeadHandler(
        aeadUtil: AeadUtil
    ): AbstractAeadHandler<RenameTuple> = RenameTupleAeadHandler(aeadUtil = aeadUtil)

}
