package com.nevidimka655.domain.notes.repository

import androidx.paging.PagingData
import com.nevidimka655.domain.notes.model.Note
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun deleteById(id: Long)

    suspend fun update(id: Long, name: String, text: String)

    suspend fun insert(name: String, text: String)

    suspend fun getById(id: Long): Note

    suspend fun getByPage(pageSize: Int, pageIndex: Int): List<Note>

    fun listOrderDescAsc(): Flow<PagingData<Note>>

}