package com.nevidimka655.tink_lab.domain.util

import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key

interface KeyGenerator {

    operator fun invoke(
        keysetPassword: String,
        keysetAssociatedData: ByteArray,
        dataType: DataType,
        aeadType: String
    ): Key

}