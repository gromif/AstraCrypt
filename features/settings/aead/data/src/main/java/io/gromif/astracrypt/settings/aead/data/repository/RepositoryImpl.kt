package io.gromif.astracrypt.settings.aead.data.repository

import io.gromif.astracrypt.settings.aead.domain.repository.Repository
import io.gromif.astracrypt.settings.aead.domain.repository.SettingsRepository

class RepositoryImpl(
    private val settingsRepository: SettingsRepository,
): Repository {



}