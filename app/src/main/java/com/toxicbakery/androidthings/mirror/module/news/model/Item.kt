package com.toxicbakery.androidthings.mirror.module.news.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item")
data class Item @JvmOverloads constructor(
        @field:Element(name = "title") var title: String = "",
        @field:Element(name = "description") var description: String = ""
)