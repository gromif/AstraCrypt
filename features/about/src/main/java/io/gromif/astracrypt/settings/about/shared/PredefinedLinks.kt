package io.gromif.astracrypt.settings.about.shared

import io.gromif.astracrypt.settings.about.model.Link

private const val APP_NAME = "AstraCrypt"
private const val EMAIL = "alexander.kobrys@proton.me"

object PredefinedLinks {
    object Communication {
        val email = Link.Email(name = "Email", email = EMAIL, emailSubject = APP_NAME)
    }

    object Market {
        object GitHub {
            val appLink =
                Link.Default(name = "GitHub", link = "https://github.com/gromif/AstraCrypt")
        }

        object Play {
            val appLink = Link.Default(
                name = "Google Play",
                link = "https://play.google.com/store/apps/details?id=com.nevidimka655.astracrypt"
            )

            fun otherAppsLink(name: String) = Link.Default(
                name = name,
                link = "https://play.google.com/store/apps/developer?id=gromif"
            )
        }
    }
}
