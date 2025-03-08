package io.gromif.astracrypt.profile.presentation.settings.aead

internal interface AeadSettingsScreenActions {

    fun onSettingsChanged(optionIndex: Int)

    companion object {
        val DEFAULT = object : AeadSettingsScreenActions {
            override fun onSettingsChanged(optionIndex: Int) {}
        }
    }

}