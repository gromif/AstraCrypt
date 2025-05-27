package io.gromif.astracrypt.files.data.repository.search

import io.gromif.astracrypt.files.domain.repository.item.ItemReader
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
    private val itemReader: ItemReader = mockk(relaxed = true)

    @Before
    fun setUp() {
        searchStrategy = DefaultSearchStrategy(itemReader = itemReader)
    }

    @Test
    fun `should correctly fetch the rootIds list and cache the result`() = runTest {
        val targetRequest = 5L

        searchStrategy.search(request = targetRequest)
        searchStrategy.search(request = targetRequest)

        coVerify(exactly = 1) { itemReader.getFolderIds(targetRequest) }
    }

    @Test
    fun `should correctly fetch the rootIds list when the result is cached`() = runTest {
        val targetRequestOld = 5L
        val targetRequestNew = 6L

        searchStrategy.search(request = targetRequestOld)
        searchStrategy.search(request = targetRequestNew)

        coVerify(exactly = 1) { itemReader.getFolderIds(targetRequestOld) }
        coVerify(exactly = 1) { itemReader.getFolderIds(targetRequestNew) }
    }

    @After
    fun tearDown() {
        confirmVerified(itemReader)
    }
}
