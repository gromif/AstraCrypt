package io.gromif.crypto.tink.core.hash

import java.security.MessageDigest

class Sha256Util : HashUtil {

    override fun compute(value: ByteArray): ByteArray = MessageDigest.getInstance("SHA-256")
        .digest(value)

}