package io.gromif.secure_content.data

import contract.secure_content.SecureContentContract
import io.gromif.secure_content.data.mapper.toContractMode
import io.gromif.secure_content.domain.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SecureContentContractImpl(
    private val settingsRepository: SettingsRepository
) : SecureContentContract {

    override fun getContractModeFlow(): Flow<SecureContentContract.Mode> {
        return settingsRepository.getSecureContentModeFlow().map { it.toContractMode() }
    }

}