package day8

import utils.DayInputs

fun main() {
    val inputs = DayInputs(8)
    println("# TEST #")
    inputs.testInput.process()
    println("# PUZZLE #")
    inputs.input.process()
}

data class Entry(val patterns: List<Digit>, val outputs: List<Digit>)
data class Digit(val segment: String, var value: Int)

fun List<String>.process() {
    val entries = initEntries()
    println("--> Result: ${entries.sumOf { entry -> entry.outputs.count { it.value != -1 } }} easy digits")
    entries.guessAllDigit()
    println("--> Result: ${entries.sumOf { entry -> entry.outputs.map { it.value }.joinToString("").toInt() }} easy digits")
}

fun List<String>.initEntries(): List<Entry> {
    return map { line ->
        val signalPattern = line.split("|").first().split(" ").filter { it.isNotEmpty() }
            .map { Digit(it.trim(), it.trim().asEasyDigit()) }
        val outputDigits = line.split("|").last().split(" ").filter { it.isNotEmpty() }
            .map { Digit(it.trim(), it.trim().asEasyDigit()) }
        Entry(signalPattern, outputDigits)
    }
}

fun List<Entry>.guessAllDigit() {
    map { it.guessDigit() }
}

fun Entry.guessDigit() {
    patterns.groupBy { it.segment.length }.let { pattern ->
        pattern[6]!!.forEach { digit ->
            when {
                digit.segment.toList().containsAll(
                    pattern[4]!![0].segment.toMutableList().union(pattern[3]!![0].segment.toMutableList())
                ) -> digit.value = 9
                digit.segment.toList().containsAll(pattern[3]!![0].segment.toMutableList()) -> digit.value = 0
                else -> digit.value = 6
            }
        }
        pattern[5]!!.forEach { digit ->
            when {
                digit.segment.toList().containsAll(pattern[2]!![0].segment.toMutableList()) -> digit.value = 3
                pattern[6]!!.filter { it.value == 6 }[0].segment.toMutableList()
                    .containsAll(digit.segment.toList()) -> digit.value = 5
                else -> digit.value = 2
            }
        }
    }
    outputs.forEach { digit ->
        repeat(patterns.size) { index ->
            patterns[index].takeIf {
                digit.segment.toMutableList()
                    .containsAll(it.segment.toMutableList()) && digit.segment.length == it.segment.length
            }?.let { digit.value = it.value }
        }
    }
}

fun String.asEasyDigit(): Int {
    return when (length) {
        2 -> 1
        4 -> 4
        3 -> 7
        7 -> 8
        else -> -1
    }
}