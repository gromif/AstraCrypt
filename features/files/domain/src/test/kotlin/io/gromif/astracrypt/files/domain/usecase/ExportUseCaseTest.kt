package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.ItemExporter
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ExportUseCaseTest {
    private lateinit var externalExportUseCase: ExternalExportUseCase
    private val itemExporter: ItemExporter = mockk(relaxed = true)

    @Before
    fun setUp() {
        externalExportUseCase = ExternalExportUseCase(itemExporter)
    }

    @Test(expected = ValidationException.EmptyIdListException::class)
    fun shouldThrowException_whenIdListIsEmpty() {
        runBlocking { externalExportUseCase(emptyList(), "") }
    }
}
