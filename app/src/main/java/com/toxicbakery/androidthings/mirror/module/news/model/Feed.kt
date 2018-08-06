package com.toxicbakery.androidthings.mirror.module.news.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss")
data class Feed @JvmOverloads constructor(
        @field:Element
        var channel: Channel = Channel()
)

