package io.gromif.notes.di

import androidx.paging.PagingData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.utils.Mapper
import io.gromif.notes.data.db.NoteItemEntity
import io.gromif.notes.data.db.NotesDao
import io.gromif.notes.data.mappers.NoteMapper
import io.gromif.notes.data.paging.PagingProviderImpl
import io.gromif.notes.data.repository.RepositoryImpl
import io.gromif.notes.data.util.AeadHandler
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.paging.PagingProvider
import io.gromif.notes.domain.repository.Repository
import io.gromif.notes.domain.repository.SettingsRepository

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    fun provideRepository(
        dao: NotesDao,
        aeadHandler: AeadHandler,
        dataToDomainMapper: Mapper<NoteItemEntity, Note>
    ): Repository = RepositoryImpl(
        dao = dao,
        aeadHandler = aeadHandler,
        noteMapper = dataToDomainMapper
    )

    @Provides
    fun providePagingProvider(
        dao: NotesDao,
        settingsRepository: SettingsRepository,
        aeadHandler: AeadHandler,
        dataToDomainMapper: Mapper<NoteItemEntity, Note>
    ): PagingProvider<PagingData<Note>> = PagingProviderImpl(
        dao = dao,
        settingsRepository = settingsRepository,
        aeadHandler = aeadHandler,
        noteMapper = dataToDomainMapper
    )

    @Provides
    fun provideNoteMapper(): Mapper<NoteItemEntity, Note> = NoteMapper()

}