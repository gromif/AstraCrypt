package io.gromif.astracrypt.profile.presentation.settings

import io.gromif.astracrypt.profile.domain.model.DefaultAvatar

interface Actions {

    fun changeName(name: String)

    fun setDefaultAvatar(defaultAvatar: DefaultAvatar)

    fun setExternalAvatar()

    companion object {
        internal val default = object : Actions {
            override fun changeName(name: String) {}
            override fun setDefaultAvatar(defaultAvatar: DefaultAvatar) {}
            override fun setExternalAvatar() {}
        }
    }

}