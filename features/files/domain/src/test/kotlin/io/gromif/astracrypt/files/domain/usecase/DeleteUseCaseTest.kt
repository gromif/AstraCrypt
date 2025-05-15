package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteUseCaseTest {
    private lateinit var deleteUseCase: DeleteUseCase
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        deleteUseCase = DeleteUseCase(getAeadInfoUseCase, repository)
    }

    @Test(expected = ValidationException.EmptyIdListException::class)
    fun shouldThrowException_whenIdListIsEmpty() {
        runBlocking { deleteUseCase(emptyList()) }
    }
}
