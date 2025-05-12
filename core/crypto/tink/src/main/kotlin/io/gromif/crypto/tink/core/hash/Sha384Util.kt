package io.gromif.crypto.tink.core.hash

import java.security.MessageDigest

class Sha384Util : HashUtil {

    override fun compute(value: ByteArray): ByteArray = MessageDigest.getInstance("SHA-384")
        .digest(value)

}