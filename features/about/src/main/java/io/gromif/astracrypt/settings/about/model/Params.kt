package io.gromif.astracrypt.settings.about.model

import io.gromif.astracrypt.settings.about.list.FakeData

data class Params(
    val version: String = "<VERSION>",
    val commonLinks: List<Link> = FakeData.linkList(),
    val supportLinks: List<Link> = FakeData.linkList(),
)
