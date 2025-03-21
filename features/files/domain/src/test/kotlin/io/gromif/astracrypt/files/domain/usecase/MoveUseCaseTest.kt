package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MoveUseCaseTest {
    private lateinit var moveUseCase: MoveUseCase
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        moveUseCase = MoveUseCase(repository)
    }

    @Test(expected = ValidationException.EmptyIdListException::class)
    fun shouldThrowException_whenIdListIsEmpty() {
        runBlocking { moveUseCase(emptyList(), 0) }
    }

}