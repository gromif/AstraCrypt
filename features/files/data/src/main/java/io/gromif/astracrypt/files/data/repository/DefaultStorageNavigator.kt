package io.gromif.astracrypt.files.data.repository

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

private typealias Folder = StorageNavigator.Folder
private typealias BackStack = MutableList<Folder>

class DefaultStorageNavigator : StorageNavigator {
    private val backStackMutableStateFlow = MutableStateFlow(emptyList<Folder>())
    private val rootFolder = Folder(id = 0, name = "")

    private val backStack get() = backStackMutableStateFlow.value

    private fun updateBackStack(block: BackStack.(BackStack) -> Unit) {
        backStackMutableStateFlow.update {
            it.toMutableList().apply { block(this) }
        }
    }

    override fun getBackStackFlow(): Flow<List<Folder>> = backStackMutableStateFlow

    override fun getCurrentFolder(): Folder {
        return backStack.lastOrNull() ?: rootFolder
    }

    override fun push(folder: StorageNavigator.Folder) {
        val existingBackStackEntryIndex = backStack.indexOfFirst {
            it == folder
        }
        val isPresentInBackStack = existingBackStackEntryIndex != -1

        updateBackStack {
            if (isPresentInBackStack) {
                val entriesToRemove = backStack.subList(
                    fromIndex = existingBackStackEntryIndex + 1,
                    toIndex = backStack.size
                )
                removeAll(entriesToRemove)
            } else add(folder)
        }
    }

    override fun pop() {
        if (backStack.isNotEmpty()) updateBackStack { removeAt(lastIndex) }
    }

    override fun swapBackStackWith(targetBackStack: List<StorageNavigator.Folder>) {
        backStackMutableStateFlow.update { targetBackStack }
    }

}
