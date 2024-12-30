package com.nevidimka655.astracrypt.utils.io

import kotlin.random.Random

class Randomizer {
    companion object {
        private const val ENG_LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
        private const val ENG_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val NUMBERS = "0123456789"
        private const val URL_SAFE_SYMBOLS = "-_.~()@,;"

        private const val URL_SAFE_DICT = ENG_LOWERCASE + ENG_UPPERCASE + NUMBERS + URL_SAFE_SYMBOLS
    }

    /**
     * Generates a random string from a custom dictionary.
     *
     * @param dict The characters to use for generation.
     * @param size The length of the generated string.
     */
    fun generateString(dict: String, size: Int, random: Random = Random.Default): String =
        (1..size).map { dict.random(random) }.joinToString("")

    /**
     * Generates a URL-safe random string.
     *
     * @param size The length of the generated string.
     */
    fun generateUrlSafeString(size: Int, random: Random = Random.Default): String =
        generateString(URL_SAFE_DICT, size, random)
}