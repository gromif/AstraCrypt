package io.gromif.astracrypt.files.data.repository.search

import io.gromif.astracrypt.files.domain.repository.search.SearchManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DefaultSearchManagerTest {
    private lateinit var searchManager: SearchManager

    @Before
    fun setUp() {
        searchManager = DefaultSearchManager()
    }

    @Test
    fun `getSearchRequestFlow should return null by default`() = runTest {
        val result = searchManager.getSearchRequestFlow().first()
        Assert.assertEquals(null, result)
    }

    @Test
    fun `setSearchRequest should correctly update the state`() = runTest {
        val targetState = "test"

        searchManager.setSearchRequest(query = targetState)

        val result = searchManager.getSearchRequestFlow().first()
        Assert.assertEquals(targetState, result)
    }
}
