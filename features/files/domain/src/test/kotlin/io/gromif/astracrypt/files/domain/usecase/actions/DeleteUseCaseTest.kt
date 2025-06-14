package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.repository.item.ItemDeleter
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteUseCaseTest {
    private lateinit var deleteUseCase: DeleteUseCase
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val itemDeleter: ItemDeleter = mockk(relaxed = true)

    @Before
    fun setUp() {
        deleteUseCase = DeleteUseCase(getAeadInfoUseCase, itemDeleter)
    }

    @Test(expected = ValidationException.EmptyIdListException::class)
    fun shouldThrowException_whenIdListIsEmpty() {
        runBlocking { deleteUseCase(emptyList()) }
    }
}
