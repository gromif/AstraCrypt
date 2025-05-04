package io.gromif.secure_content.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import io.gromif.secure_content.domain.SecureContentMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

private const val DATASTORE_NAME = "test"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class SettingsRepositoryImplTest {
    private lateinit var settingsRepository: SettingsRepositoryImpl
    private lateinit var dataStore: DataStore<Preferences>
    private val scope = TestScope(UnconfinedTestDispatcher() + Job())

    @Before
    fun setUp() {
        dataStore = PreferenceDataStoreFactory.create(
            scope = scope,
            produceFile = {
                RuntimeEnvironment.getApplication().preferencesDataStoreFile(DATASTORE_NAME)
            }
        )
        settingsRepository = SettingsRepositoryImpl(dataStore)
    }

    @Test
    fun `setSecureContentMode should correctly update preferences`() = runTest {
        val dataStoreKey = intPreferencesKey("secureContent_mode")
        val targetMode = SecureContentMode.DISABLED

        settingsRepository.setSecureContentMode(mode = targetMode)

        val savedValue = dataStore.data.first()[dataStoreKey]
        Assert.assertEquals(0, savedValue)
    }

    @Test
    fun `getSecureContentModeFlow should correctly return the saved value`() = runTest {
        val targetMode = SecureContentMode.FORCE
        settingsRepository.setSecureContentMode(mode = targetMode)

        val savedValue = settingsRepository.getSecureContentModeFlow().first()
        Assert.assertEquals(targetMode, savedValue)
    }

    @Test
    fun `getSecureContentModeFlow should return the correct default value`() = runTest {
        val targetMode = SecureContentMode.ENABLED

        val defaultValue = settingsRepository.getSecureContentModeFlow().first()
        Assert.assertEquals(targetMode, defaultValue)
    }

}