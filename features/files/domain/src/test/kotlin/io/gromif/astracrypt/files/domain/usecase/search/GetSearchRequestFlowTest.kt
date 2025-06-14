package io.gromif.astracrypt.files.domain.usecase.search

import io.gromif.astracrypt.files.domain.repository.search.SearchManager
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetSearchRequestFlowTest {
    private lateinit var getSearchRequestFlow: GetSearchRequestFlow
    private val searchManagerMock: SearchManager = mockk()

    @Before
    fun setUp() {
        getSearchRequestFlow = GetSearchRequestFlow(
            searchManager = searchManagerMock
        )
    }

    @Test
    fun `should return the correct searchRequestFlow`() {
        val targetFlow = flowOf("test")

        every { searchManagerMock.getSearchRequestFlow() } returns targetFlow

        val result = getSearchRequestFlow()
        Assert.assertSame(targetFlow, result)

        verify(exactly = 1) { getSearchRequestFlow() }
    }

    @After
    fun tearDown() {
        confirmVerified(searchManagerMock)
    }
}
