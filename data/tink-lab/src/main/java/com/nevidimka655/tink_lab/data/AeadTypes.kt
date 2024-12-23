package com.nevidimka655.tink_lab.data

import com.nevidimka655.crypto.tink.domain.KeysetTemplates

val AeadTypesText = KeysetTemplates.AEAD.entries.map { it.name.lowercase() }

val AeadTypesFiles = KeysetTemplates.Stream.entries
    .filter { it.name.endsWith("MB") }
    .map { it.name.removeSuffix("_1MB").lowercase() }