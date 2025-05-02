package io.gromif.device_admin_api

import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AdminReceiverTest {
    private lateinit var adminReceiver: AdminReceiver

    @Before
    fun setUp() {
        adminReceiver = AdminReceiver()
    }

    @Test
    fun `should correctly update the receiver state when admin rights are disabled`() = runTest {
        adminReceiver.onDisabled(mockk(), mockk())
        val receiverStatus = AdminReceiver.receiverAdminStatusFlow.first()
        Assert.assertEquals(false, receiverStatus)
    }

    @Test
    fun `should correctly update the receiver state when admin rights are enabled`() = runTest {
        adminReceiver.onEnabled(mockk(), mockk())
        val receiverStatus = AdminReceiver.receiverAdminStatusFlow.first()
        Assert.assertEquals(true, receiverStatus)
    }

}