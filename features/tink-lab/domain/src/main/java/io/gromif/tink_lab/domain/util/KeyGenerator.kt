package io.gromif.tink_lab.domain.util

import io.gromif.tink_lab.domain.model.DataType
import io.gromif.tink_lab.domain.model.Key

interface KeyGenerator {

    operator fun invoke(dataType: DataType, aeadType: String): Key

}