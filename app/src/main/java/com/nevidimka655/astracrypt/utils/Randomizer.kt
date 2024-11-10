package com.nevidimka655.astracrypt.utils

import kotlin.random.Random

object Randomizer {

    private const val englishLiterals = "abcdefghijklmnopqrstuvwxyz"
    private const val englishLiteralsUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private const val numbers = "1234567890"
    private const val symbolsUrlSafe = "-_.~()@,;"

    private const val urlSafeDictionary = "$englishLiterals$englishLiteralsUpper" +
            "$numbers$symbolsUrlSafe"

    fun genStr(customDict: String, size: Int): String {
        val str = StringBuilder()
        repeat(size) {
            str.append(customDict.random())
        }
        return str.toString()
    }

    fun getUrlSafeString(size: Int) = StringBuilder().apply {
        repeat(size) { append(urlSafeDictionary.random()) }
    }.toString()

    fun getUrlSafeString(random: Random, size: Int) = StringBuilder().apply {
        repeat(size) { append(urlSafeDictionary.random(random)) }
    }.toString()

}