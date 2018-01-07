package com.toxicbakery.androidthings.mirror.module.newsfeed.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss")
data class Feed @JvmOverloads constructor(
        @field:Element
        var channel: Channel = Channel()
)

@Root(name = "channel")
data class Channel @JvmOverloads constructor(
        @field:ElementList(entry = "item", inline = true)
        var items: List<Item> = mutableListOf()
)

@Root(name = "item")
data class Item @JvmOverloads constructor(
        @field:Element(name = "title") var title: String = "",
        @field:Element(name = "description") var description: String = ""
)