package io.gromif.donate.model

import io.gromif.donate.R

@Suppress(
    "detekt:MaximumLineLength",
    "detekt:MaxLineLength"
)
internal object Currencies {

    val default = listOf(
        Currency(
            name = "Bitcoin (BTC)",
            address = "bc1qfp4qxe6zp69s6fnuyyam7n9vvu92467n2xtpc3",
            iconResId = R.drawable.currency_btc
        ),
        Currency(
            name = "Ethereum (ETH)",
            address = "0x322b8c1C305b3Ec997B90CBe8EF8402ec37a03d3",
            iconResId = R.drawable.currency_eth
        ),
        Currency(
            name = "Monero (XMR)",
            address = "47cfoZG7MtqLu9wFsQbb6h7K4wkBZZTnuhZX8VTcByzZTWafDX5CT9KMtrChjoDEwqLXdrRYXD36WMwSff7bHWhYLihLqWM",
            iconResId = R.drawable.currency_xmr
        ),
        Currency(
            name = "Litecoin (LTC)",
            address = "ltc1qm7mf9v2j7cpxxehc67smk0s7me3cnrrc3vpcwu",
            iconResId = R.drawable.currency_ltc
        ),
        Currency(
            name = "Cardano (ADA)",
            address = "addr1q8rd94y99cp0nx77azw7h35zwhegnj4hscvjjxtds0plgp4v4tw9q8larcacjrgp3ykhgsu4vhu4drazrwyh5g0r630sg97vag",
            iconResId = R.drawable.currency_ada
        ),
    )
}
