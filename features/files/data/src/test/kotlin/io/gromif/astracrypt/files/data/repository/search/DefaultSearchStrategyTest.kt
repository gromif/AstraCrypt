package io.gromif.astracrypt.files.data.repository.search

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.search.SearchStrategy
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class DefaultSearchStrategyTest {
    private lateinit var searchStrategy: SearchStrategy<Long, List<Long>>
    private val repositoryMock: Repository = mockk(relaxed = true)

    @Before
    fun setUp() {
        searchStrategy = DefaultSearchStrategy(repository = repositoryMock)
    }

    @Test
    fun `should correctly fetch the rootIds list`() = runTest {
        val targetRequest = 5L

        searchStrategy.search(request = targetRequest)

        coVerify(exactly = 1) { repositoryMock.getFolderIds(targetRequest) }
    }

    @After
    fun tearDown() {
        confirmVerified(repositoryMock)
    }

}