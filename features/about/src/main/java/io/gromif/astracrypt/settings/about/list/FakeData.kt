package io.gromif.astracrypt.settings.about.list

import io.gromif.astracrypt.settings.about.model.Link
import io.gromif.astracrypt.settings.about.shared.PredefinedLinks

internal object FakeData {

    fun linkList() = listOf(
        Link.Default(
            name = "More apps",
            description = "description",
            link = ""
        ),
        PredefinedLinks.Communication.email,
        Link.PrivacyPolicy
    )
}
