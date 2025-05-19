package io.gromif.astracrypt.files.domain.repository

import kotlinx.coroutines.flow.Flow

interface StorageNavigator {

    fun getBackStackFlow(): Flow<List<Folder>>

    fun getCurrentFolder(): Folder

    fun getCurrentFolderFlow(): Flow<Folder>

    fun push(folder: Folder)

    fun pop()

    fun swapBackStackWith(targetBackStack: List<Folder>)

    data class Folder(
        val id: Long,
        val name: String
    )
}
