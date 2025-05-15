package io.gromif.astracrypt.auth.domain.model

private const val IMMEDIATELY_VALUE = 1
private const val SECONDS_5_VALUE = 5
private const val SECONDS_10_VALUE = 10
private const val SECONDS_15_VALUE = 15
private const val SECONDS_30_VALUE = 30
private const val SECONDS_60_VALUE = 60
private const val NEVER_VALUE = -1

enum class Timeout(val seconds: Int) {

    IMMEDIATELY(IMMEDIATELY_VALUE),
    SECONDS_5(SECONDS_5_VALUE),
    SECONDS_10(SECONDS_10_VALUE),
    SECONDS_15(SECONDS_15_VALUE),
    SECONDS_30(SECONDS_30_VALUE),
    SECONDS_60(SECONDS_60_VALUE),
    NEVER(NEVER_VALUE)
}
