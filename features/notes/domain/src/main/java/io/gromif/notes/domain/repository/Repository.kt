package io.gromif.notes.domain.repository

import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.model.Note

interface Repository {

    suspend fun deleteById(id: Long)

    suspend fun update(
        aead: AeadMode,
        id: Long,
        name: String,
        text: String,
    )

    suspend fun insert(
        aead: AeadMode,
        name: String,
        text: String,
    )

    suspend fun getById(aead: AeadMode, id: Long): Note

    suspend fun changeAead(
        sourceAeadMode: AeadMode,
        targetAeadMode: AeadMode,
    )

}