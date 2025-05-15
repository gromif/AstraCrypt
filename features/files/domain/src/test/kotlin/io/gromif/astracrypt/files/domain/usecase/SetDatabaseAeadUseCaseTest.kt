package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SetDatabaseAeadUseCaseTest {
    private lateinit var setDatabaseAeadUseCase: SetDatabaseAeadUseCase
    private val setAeadInfoUseCase: SetAeadInfoUseCase = mockk()
    private val getAeadInfoUseCase: GetAeadInfoUseCase = mockk()
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        setDatabaseAeadUseCase = SetDatabaseAeadUseCase(setAeadInfoUseCase, getAeadInfoUseCase, repository)
    }

    @Test
    fun shouldGetCurrentAeadInfo_and_updateRepository() {
        val currentAeadInfo: AeadInfo = mockk()
        val targetAeadInfo: AeadInfo = mockk()

        coEvery { getAeadInfoUseCase() } returns currentAeadInfo
        coJustRun { setAeadInfoUseCase(targetAeadInfo) }

        runBlocking { setDatabaseAeadUseCase(targetAeadInfo) }

        coVerifyOrder {
            getAeadInfoUseCase()
            repository.changeAead(
                oldAeadInfo = currentAeadInfo,
                targetAeadInfo = targetAeadInfo
            )
            setAeadInfoUseCase(targetAeadInfo)
        }
    }
}
