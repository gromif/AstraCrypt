package io.gromif.notes.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import io.gromif.astracrypt.utils.Mapper
import io.gromif.notes.data.db.NoteItemEntity
import io.gromif.notes.data.db.NotesDao
import io.gromif.notes.data.util.AeadHandler
import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.paging.PagingProvider
import io.gromif.notes.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PagingProviderImpl(
    private val dao: NotesDao,
    private val settingsRepository: SettingsRepository,
    private val aeadHandler: AeadHandler,
    private val noteMapper: Mapper<NoteItemEntity, Note>,
): PagingProvider<PagingData<Note>> {

    override operator fun invoke(): Flow<PagingData<Note>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                dao.listOrderDescAsc()
            }
        ).flow.map { pagingData ->
            val aead = settingsRepository.getAeadMode()
            if (aead is AeadMode.Template) pagingData.map {
                val currentItem = aeadHandler.decryptNoteEntity(aead, it)
                noteMapper(item = currentItem)
            } else pagingData.map { noteMapper(item = it) }
        }
    }

}