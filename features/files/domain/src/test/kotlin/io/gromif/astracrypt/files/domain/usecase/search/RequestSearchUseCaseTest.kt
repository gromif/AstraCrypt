package io.gromif.astracrypt.files.domain.usecase.search

import io.gromif.astracrypt.files.domain.repository.search.SearchManager
import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class RequestSearchUseCaseTest {
    private lateinit var requestSearchUseCase: RequestSearchUseCase
    private val searchManagerMock: SearchManager = mockk()

    @Before
    fun setUp() {
        requestSearchUseCase = RequestSearchUseCase(
            searchManager = searchManagerMock
        )
    }

    @Test
    fun `should correctly send the request`() {
        val targetRequest = "test"

        justRun { searchManagerMock.setSearchRequest(targetRequest) }

        requestSearchUseCase(targetRequest)

        verify(exactly = 1) { searchManagerMock.setSearchRequest(targetRequest) }
    }

    @After
    fun tearDown() {
        confirmVerified(searchManagerMock)
    }

}
