package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ExportUseCaseTest {
    private lateinit var exportUseCase: ExportUseCase
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        exportUseCase = ExportUseCase(repository)
    }

    @Test(expected = ValidationException.EmptyIdListException::class)
    fun shouldThrowException_whenIdListIsEmpty() {
        runBlocking { exportUseCase(emptyList(), "") }
    }

}