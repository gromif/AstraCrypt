package io.gromif.tinkLab.domain.model.result

import io.gromif.tinkLab.domain.model.Key

sealed interface ReadKeyResult {

    @JvmInline
    value class Success(val key: Key) : ReadKeyResult

    object Error : ReadKeyResult
}
