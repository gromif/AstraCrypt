package io.gromif.astracrypt.files.data.repository

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

private typealias Folder = StorageNavigator.Folder

class DefaultStorageNavigatorTest {
    private lateinit var storageNavigator: StorageNavigator
    private val predefinedBackStack = (1..5).map {
        Folder(it.toLong(), "Folder$it")
    }

    @Before
    fun setUp() {
        storageNavigator = DefaultStorageNavigator()
    }

    @Test
    fun `getCurrentFolderFlow should return the last folder when backStack is not empty`() = runTest {
        val targetFolder = Folder(id = 24, name = "Test")

        storageNavigator.push(targetFolder)
        val result = storageNavigator.getCurrentFolderFlow().first()

        Assert.assertSame(targetFolder, result)
    }

    @Test
    fun `getCurrentFolderFlow should return the rootFolder when backStack is empty`() = runTest {
        val targetRootFolder = Folder(id = 0, name = "")
        val result = storageNavigator.getCurrentFolderFlow().first()
        Assert.assertEquals(targetRootFolder, result)
    }

    @Test
    fun `swapBackStackWith should swap the backStack with targetBackStack`() = runTest {
        storageNavigator.swapBackStackWith(predefinedBackStack)
        val result = storageNavigator.getBackStackFlow().first()

        Assert.assertSame(predefinedBackStack, result)
    }

    @Test
    fun `push should add the entry to the backStack when it's not in the backStack`() = runTest {
        val targetFolder = Folder(id = 24, name = "Test")

        storageNavigator.push(targetFolder)
        val result = storageNavigator.getBackStackFlow().first()

        Assert.assertSame(targetFolder, result.first())
    }

    @Test
    fun `push should truncate the backStack when the record is in the backStack`() = runTest {
        val targetFolder = predefinedBackStack[2]
        val targetBackStack = predefinedBackStack.toMutableList().apply {
            val entriesToRemove = subList(fromIndex = 3, toIndex = size)
            removeAll(entriesToRemove)
        }.toTypedArray()

        storageNavigator.swapBackStackWith(predefinedBackStack)
        storageNavigator.push(targetFolder)
        val result = storageNavigator.getBackStackFlow().first().toTypedArray()

        val contentMatchesCondition = result.contentEquals(targetBackStack)
        Assert.assertTrue(contentMatchesCondition)
    }

    @Test
    fun `pop should remove the last folder from the backStack`() = runTest {
        val targetBackStack = predefinedBackStack.toMutableList().apply {
            removeAt(lastIndex)
        }.toTypedArray()

        storageNavigator.swapBackStackWith(predefinedBackStack)
        storageNavigator.pop()
        val result = storageNavigator.getBackStackFlow().first().toTypedArray()

        val contentMatchesCondition = result.contentEquals(targetBackStack)
        Assert.assertTrue(contentMatchesCondition)
    }
}
