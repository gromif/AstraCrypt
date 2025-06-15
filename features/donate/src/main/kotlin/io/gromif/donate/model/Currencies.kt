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
            address = "bc1pdsudp0ydnlej4s7ekuh0g3vw6npheqv3sq5jk7tncrc22zekulgqyfeqgs",
            iconResId = R.drawable.currency_btc
        ),
        Currency(
            name = "Ethereum (ETH)",
            address = "0x6AB42792BA41b88983d7d7384B1162210341AC46",
            iconResId = R.drawable.currency_eth
        ),
        Currency(
            name = "Monero (XMR)",
            address = "42o8RetYnV7PmdENJKg6SvVqWSUy8QBYxECSbp6xreQ6PQ5EETjXVNS7ssqyRrLjMcVL6FAoUyK7tQfwKMNbb3JdLkT1HLK",
            iconResId = R.drawable.currency_xmr
        ),
        Currency(
            name = "Litecoin (LTC)",
            address = "ltc1qch299ndm87a5ssfusxqrnfpwtc4348fpvhys7y",
            iconResId = R.drawable.currency_ltc
        ),
        Currency(
            name = "Cardano (ADA)",
            address = "addr1qyyjl0d6sqmxmzq04vjwvg29h6hcvzavdps465htrnlj5zkt3vrxy6lt068kdvlymj45mn70k0c0hqxg8az6ya7r8xwsz2g35p",
            iconResId = R.drawable.currency_ada
        ),
    )
}
