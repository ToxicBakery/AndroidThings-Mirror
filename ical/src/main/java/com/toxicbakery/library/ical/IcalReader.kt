package com.toxicbakery.library.ical

import java.io.BufferedReader

/**
 * Ical format reader.
 */
class IcalReader {

    fun readIcal(reader: BufferedReader): Ical {
        val blocks: MutableList<MutableBlock> = mutableListOf()
        do {
            val line = reader.readLine() ?: break
            when {
                line.startsWith(BEGIN_BLOCK) -> {
                    blocks.add(MutableBlock(line.substring(BEGIN_BLOCK.length)))
                }
                line.startsWith(END_BLOCK) -> Unit
                line.startsWith(" ") -> {
                    blocks.last()
                            .values
                            .apply { put(DESCRIPTION, get(DESCRIPTION) + unescape(line)) }
                }
                else -> parseLineValues(line).let { (key, value) -> blocks.last().values[key] = unescape(value) }
            }
        } while (true)

        return blocks.first()
                .let { rootBlock: MutableBlock ->
                    blocks.subList(1, blocks.size)
                            .map { it as Block }
                            .toList()
                            .let { blocks: List<Block> -> Ical(rootBlock.values, blocks) }
                }
    }

    private fun unescape(line: String) =
            line.trim()
                    .replace("\\n", "\n")
                    .replace("\\,", ",")
                    .replace("\\:", ":")

    private fun parseLineValues(line: String): Pair<String, String> =
            line.split(":", limit = 2)
                    .also { if (it.size < 2) throw Exception("Unexpected line $it") }
                    .let { Pair(it[0], it[1]) }

    companion object {
        private const val BEGIN_BLOCK = "BEGIN:"
        private const val END_BLOCK = "END:"
        private const val DESCRIPTION = "DESCRIPTION"
    }

}

internal data class MutableBlock(
        override val type: String,
        override val values: MutableMap<String, String> = mutableMapOf()
) : Block