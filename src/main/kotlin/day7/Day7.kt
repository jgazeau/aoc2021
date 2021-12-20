package day7

import utils.DayInputs
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.abs

fun main() {
    val inputs = DayInputs(7)
    println("# TEST #")
    inputs.testInput.process()
    inputs.testInput.process(incremental = true)
    println("# PUZZLE #")
    inputs.input.process()
    inputs.input.process(incremental = true)
}

fun List<String>.process(incremental: Boolean = false) {
    var minFuel = MAX_VALUE
    val crabs = first().initPopulation()
    crabs.maxRange().forEach { finalPosition ->
        minFuel = minOf(crabs.sumOf { crabPosition ->
            abs(crabPosition - finalPosition).let { fuel ->
                fuel.takeIf { fuel != 0 && incremental }?.let { (1..it).reduce(Int::plus) } ?: fuel
            }
        }, minFuel)
    }
    println("--> Result: $minFuel fuel found ${takeIf { incremental }?.let { "for incremental" }}")
}

fun String.initPopulation(): List<Int> = split(",").map { it.trim().toInt() }
fun List<Int>.maxRange() = (minOrNull()!!..maxOrNull()!!)