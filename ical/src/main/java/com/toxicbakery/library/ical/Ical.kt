package com.toxicbakery.library.ical

data class Ical(
        val values: Map<String, String> = mapOf(),
        val blocks: List<Block> = listOf()
)