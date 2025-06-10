package io.gromif.tinkLab.domain.util

import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key

interface KeyGenerator {

    operator fun invoke(dataType: DataType, aeadType: String): Key
}
