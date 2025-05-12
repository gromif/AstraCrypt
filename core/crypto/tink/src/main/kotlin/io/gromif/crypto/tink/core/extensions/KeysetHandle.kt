package io.gromif.crypto.tink.core.extensions

import com.google.crypto.tink.Aead
import com.google.crypto.tink.DeterministicAead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.StreamingAead
import com.google.crypto.tink.prf.PrfSet

private val REGISTRY = RegistryConfiguration.get()

fun KeysetHandle.streamingAead(): StreamingAead = getPrimitive(REGISTRY, StreamingAead::class.java)

fun KeysetHandle.aead(): Aead = getPrimitive(REGISTRY, Aead::class.java)

fun KeysetHandle.deterministicAead(): DeterministicAead =
    getPrimitive(REGISTRY, DeterministicAead::class.java)

fun KeysetHandle.prf(): PrfSet = getPrimitive(REGISTRY, PrfSet::class.java)