package com.toxicbakery.androidthings.mirror.module.news.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel")
data class Channel @JvmOverloads constructor(
        @field:ElementList(entry = "item", inline = true)
        var items: List<Item> = mutableListOf()
)