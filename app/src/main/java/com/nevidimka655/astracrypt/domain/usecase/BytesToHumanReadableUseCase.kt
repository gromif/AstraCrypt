package com.nevidimka655.astracrypt.domain.usecase

import java.text.DecimalFormat
import javax.inject.Inject

class BytesToHumanReadableUseCase @Inject constructor() {

    fun invoke(length: Long): String {
        val kilobytes = length / 1024.0
        val megabytes = kilobytes / 1024.0
        val gigabytes = megabytes / 1024.0
        val terabytes = gigabytes / 1024.0
        val decimalFormat = DecimalFormat("0.00")
        return when {
            terabytes > 1 -> decimalFormat.format(terabytes) + " Tb"
            gigabytes > 1 -> decimalFormat.format(gigabytes) + " Gb"
            megabytes > 1 -> decimalFormat.format(megabytes) + " Mb"
            kilobytes > 1 -> decimalFormat.format(kilobytes) + " Kb"
            else -> decimalFormat.format(length) + " B"
        }
    }

}