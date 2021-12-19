package day4

import utils.DayInputs

fun main() {
    val inputs = DayInputs(4)
    println("# TEST #")
    inputs.testInput.process()
    println("# PUZZLE #")
    inputs.input.process()
}

data class Bingo(
    val draws: List<Int>,
    val grids: List<Grid>,
    var winnerGrid: Grid? = null,
    var lastWinnerGrid: Grid? = null,
    var winnerDraw: Int = 0,
    var lastWinnerDraw: Int = 0,
    var winnerResult: Int = 0,
    var lastWinnerResult: Int = 0,
)

data class Grid(val values: List<List<GridValue>>, var finished: Boolean = false)
data class GridValue(val value: Int, var picked: Boolean = false)

fun List<String>.process() {
    createBingo().process()
}

fun List<String>.createBingo(): Bingo {
    return Bingo(this[0].split(',').map { it.toInt() },
        this.drop(1).filter { it.isNotBlank() }.windowed(5, 5).map { rawGrid ->
            Grid(rawGrid.map { row ->
                row.split(" ").filter { it.isNotBlank() }.map { GridValue(it.toInt()) }.toList()
            })
        })
}

fun Bingo.process() {
    for ((drawIndex, draw) in draws.withIndex()) {
        for ((gridIndex, grid) in grids.withIndex()) {
            grid.values.forEach { row ->
                row.forEach { it.takeUnless { it.picked }?.applyDraw(draw) }
            }
            grid.apply { finished = checkGridsResult() }
            grid.takeIf { winnerGrid == null && it.finished }?.let {
                winnerGrid = it
                winnerDraw = draw
                winnerResult = it.getGridResult()
                println("Winner grid $gridIndex found for draw $drawIndex with value $winnerDraw")
            }
            grid.takeIf { grids.filter { grid -> grid.finished }.size == grids.size && lastWinnerGrid == null && it.finished }
                ?.let {
                    lastWinnerGrid = it
                    lastWinnerDraw = draw
                    lastWinnerResult = it.getGridResult()
                    println("Last winner grid $gridIndex found for draw $drawIndex with value $lastWinnerDraw")
                }
        }
    }
    println("--> Winner result     : ${winnerResult.times(winnerDraw)} ($winnerResult*$winnerDraw)")
    println("--> Last winner result: ${lastWinnerResult.times(lastWinnerDraw)} ($lastWinnerResult*$lastWinnerDraw)")
}

fun GridValue.applyDraw(draw: Int) {
    this.picked = this.value == draw
}

fun Grid.checkGridsResult(): Boolean = checkRows().or(checkColumns())
fun Grid.checkRows(): Boolean = values.any { row -> row.all { it.picked } }
fun Grid.checkColumns(): Boolean {
    for (index in 1..values.size) {
        takeIf { values.map { it[index - 1] }.all { it.picked } }?.let { return true }
    }
    return false
}

fun Grid.getGridResult(): Int = values.flatten().filter { !it.picked }.sumOf { it.value }

