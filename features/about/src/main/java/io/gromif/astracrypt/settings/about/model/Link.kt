package io.gromif.astracrypt.settings.about.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class Link {

    data class Default(
        val name: String,
        val description: String? = null,
        val link: String,
    ) : Link()

    data object PrivacyPolicy : Link()

    data class Email(
        val name: String,
        val description: String? = null,
        val email: String,
        val emailSubject: String? = null,
    ) : Link()
}
