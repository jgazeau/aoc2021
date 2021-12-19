package day6

import utils.DayInputs

fun main() {
    val inputs = DayInputs(6)
    println("# TEST #")
    inputs.testInput.process()
    println("# PUZZLE #")
    inputs.input.process()
}

data class Population(var fishes: MutableList<Long>)

fun List<String>.process() {
    val duration = 256
    val lifecycle = first().initPopulation()
    repeat(duration) { lifecycle.iterate() }
    println("--> Result: ${lifecycle.fishes.sum()} fish")
}

fun String.initPopulation(): Population = Population(MutableList(9) { index -> split(",").count { it.toInt() == index }.toLong() })
fun Population.iterate() {
    fishes = MutableList(fishes.size) { index ->
        when (index) {
            8 -> fishes[0]
            6 -> fishes[7] + fishes[0]
            else -> fishes[index + 1]
        }
    }
}