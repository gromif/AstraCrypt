package io.gromif.astracrypt.auth.presentation.settings.model

import androidx.compose.runtime.Immutable
import io.gromif.astracrypt.auth.domain.model.Timeout

@Immutable
internal interface Actions {

    fun disableAuth()
    fun disableSkin()

    fun changePassword(password: String)
    suspend fun verifyPassword(password: String): Boolean

    fun setTimeout(timeout: Timeout)
    fun setBindAuthState(state: Boolean, password: String)

    fun setCalculatorSkin(combination: String)

    fun setHintState(state: Boolean)
    fun setHintText(text: String)

    companion object {
        internal val default = object : Actions {
            override fun disableAuth() {}
            override fun disableSkin() {}
            override fun changePassword(password: String) {}
            override suspend fun verifyPassword(password: String): Boolean = false
            override fun setTimeout(timeout: Timeout) {}
            override fun setBindAuthState(state: Boolean, password: String) {}
            override fun setCalculatorSkin(combination: String) {}
            override fun setHintState(state: Boolean) {}
            override fun setHintText(text: String) {}
        }
    }

}