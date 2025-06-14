package io.gromif.crypto.tink.core.extensions

import java.security.SecureRandom
import kotlin.random.asKotlinRandom

fun secureRandom(secureSeed: ByteArray) = SecureRandom()
    .apply { setSeed(secureSeed) }
    .asKotlinRandom()