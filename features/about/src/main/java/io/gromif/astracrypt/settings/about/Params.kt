package io.gromif.astracrypt.settings.about

import io.gromif.astracrypt.settings.about.list.FakeData
import io.gromif.astracrypt.settings.about.model.Link

data class Params(
    val version: String = "<VERSION>",
    val commonLinks: List<Link> = FakeData.linkList(),
    val supportLinks: List<Link> = FakeData.linkList(),
)
