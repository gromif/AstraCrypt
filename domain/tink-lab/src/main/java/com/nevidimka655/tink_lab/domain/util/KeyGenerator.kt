package com.nevidimka655.tink_lab.domain.util

import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key

interface KeyGenerator {

    operator fun invoke(dataType: DataType, aeadType: String): Key

}