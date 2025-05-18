package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetCurrentNavFolderUseCaseTest {
    private lateinit var getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase
    private val storageNavigatorMock: StorageNavigator = mockk()

    @Before
    fun setUp() {
        getCurrentNavFolderUseCase = GetCurrentNavFolderUseCase(
            storageNavigator = storageNavigatorMock
        )
    }

    @Test
    fun `should correctly return the current folder`() {
        val targetFolder = StorageNavigator.Folder(6, "Test")

        every { storageNavigatorMock.getCurrentFolder() } returns targetFolder

        val result = getCurrentNavFolderUseCase()

        Assert.assertSame(targetFolder, result)
        verify(exactly = 1) { storageNavigatorMock.getCurrentFolder() }
    }

    @After
    fun tearDown() {
        confirmVerified(storageNavigatorMock)
    }
}