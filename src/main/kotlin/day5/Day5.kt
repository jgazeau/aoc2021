package day5

import utils.DayInputs

fun main() {
    val inputs = DayInputs(5)
    println("# TEST #")
    inputs.testInput.process()
    println("# PUZZLE #")
    inputs.input.process()
}

data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int)
data class Diagram(val lines: List<Line>, var grid: Array<Array<Int>>)

fun List<String>.process() {
    val diagram = createDiagram()
    println("--> Result for straight lines: ${diagram.processStraights().checkResults()}")
    println("--> Result for diagonal lines: ${diagram.processDiagonals().checkResults()}")
}

fun List<String>.createDiagram(): Diagram {
    val lines = map { it.parseLine() }.toList()
    val maxX = lines.maxOf { maxOf(it.x1, it.x2) }
    val maxY = lines.maxOf { maxOf(it.y1, it.y2) }
    return Diagram(
        map { it.parseLine() }.toList(),
        Array(maxY + 1) { Array(maxX + 1) { 0 } },
    )
}

fun Diagram.processStraights(): Diagram {
    lines.filterStraight().forEach { line ->
        line.xRange().forEach { x ->
            line.yRange().forEach { y ->
                grid[y][x] = grid[y][x].inc()
            }
        }
    }
    return this
}

fun Diagram.processDiagonals(): Diagram {
    lines.filterDiagonals().forEach { line ->
        line.diagonalRange().forEach { (x, y) ->
            grid[y][x] = grid[y][x].inc()
        }
    }
    return this
}

fun Diagram.checkResults(): Int = grid.sumOf { x -> x.count { it >= 2 } }
fun List<Line>.filterStraight(): List<Line> = filter { it.x1 == it.x2 || it.y1 == it.y2 }
fun List<Line>.filterDiagonals(): List<Line> = filter { it.x1 != it.x2 && it.y1 != it.y2 }
fun Line.xRange() = (minOf(x1, x2)..maxOf(x1, x2)).toList().let { it.takeIf { x1 > x2 }?.reversed() ?: it }
fun Line.yRange() = (minOf(y1, y2)..maxOf(y1, y2)).toList().let { it.takeIf { y1 > y2 }?.reversed() ?: it }
fun Line.diagonalRange(): List<Pair<Int, Int>> {
    return xRange().zip(yRange())
}

fun String.parseLine(): Line {
    return split("->").zipWithNext().single().let {
        val firstCoordinates = it.first.parseCoordinates()
        val secondCoordinates = it.second.parseCoordinates()
        Line(firstCoordinates.first, firstCoordinates.second, secondCoordinates.first, secondCoordinates.second)
    }
}

fun String.parseCoordinates(): Pair<Int, Int> {
    return split(",").map { it.trim().toInt() }.zipWithNext().single()
}