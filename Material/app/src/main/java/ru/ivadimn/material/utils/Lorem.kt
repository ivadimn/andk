package ru.ivadimn.material.utils

import kotlin.random.Random

object Lorem {
    private val alphabet = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j",
        "k", "l", "z", "x", "c", "v", "b", "n", "m")

    private fun generateWord(isFirst : Boolean) : String {
        val letterCount = Random.nextInt(2, 11)
        val strBuilder = StringBuilder()
        (1 .. letterCount).forEach { _ ->
            strBuilder.append(alphabet.random())
        }
        if (isFirst)
            strBuilder.replace(0, 0, strBuilder[0].uppercase())

        return strBuilder.toString()
    }

    fun generate(countWords : Int)  : String {
        val sentences = mutableListOf<String>()
        sentences.add(generateWord(true))
        (2 until countWords).forEach {
            sentences.add(generateWord(false))
        }
        sentences.add("${generateWord(false)}.")
        return sentences.joinToString(" ")
    }
}