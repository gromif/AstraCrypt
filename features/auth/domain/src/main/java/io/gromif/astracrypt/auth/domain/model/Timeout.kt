package io.gromif.astracrypt.auth.domain.model

enum class Timeout(val seconds: Int) {

    IMMEDIATELY(1),
    SECONDS_5(5),
    SECONDS_10(10),
    SECONDS_15(15),
    SECONDS_30(30),
    SECONDS_60(60),
    NEVER(-1)

}