package contract.secureContent

import kotlinx.coroutines.flow.Flow

interface SecureContentContract {

    fun getContractModeFlow(): Flow<Mode>

    enum class Mode {
        DISABLED,
        ENABLED,
        FORCE
    }
}
