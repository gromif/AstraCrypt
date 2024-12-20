package com.nevidimka655.astracrypt.data.paging

import android.text.format.DateFormat
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.map
import com.nevidimka655.astracrypt.data.repository.notes.NotesRepositoryProvider
import com.nevidimka655.astracrypt.data.database.NotesPagerTuple
import com.nevidimka655.notes.Notes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesPagingProvider @Inject constructor(
    private val notesRepositoryProvider: NotesRepositoryProvider
) {
    private val pagingSource = MutableStateFlow<PagingSource<Int, NotesPagerTuple>?>(null)

    fun invalidate() { pagingSource.value?.invalidate() }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun createPagingSource() = notesRepositoryProvider.notesRepository.flatMapLatest { repo ->
        Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                repo.listOrderDescAsc().also { pagingSource.value = it }
            }
        ).flow.map { pagingData ->
            val timePattern = "d MMMM yyyy"
            pagingData.map {
                Notes.Item(
                    id = it.id,
                    title = it.name,
                    textPreview = it.textPreview?.run { "$this..." },
                    creationTime = DateFormat.format(timePattern, it.creationTime).toString()
                )
            }
        }
    }

}