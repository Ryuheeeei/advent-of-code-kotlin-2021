package day11

import readInput


fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        val grid = initializeGrid(input)
        var gridExplode = initializeGridExplode(input)

        for (i in 1..100) {
            gridExplode = initializeGridExplode(input)
            grid.forEachIndexed { row, list ->
                list.forEachIndexed { col, _ ->
                    grid.inc(row, col, gridExplode)
                }
            }
            result += gridExplode.flatten().filter { it == 1 }.count()
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        val grid = initializeGrid(input)
        var gridExplode = initializeGridExplode(input)
        var i = 1

        while (true) {
            gridExplode = initializeGridExplode(input)
            grid.forEachIndexed { row, list ->
                list.forEachIndexed { col, _ ->
                    grid.inc(row, col, gridExplode)
                }
            }
            if (gridExplode.flatten().filter { it == 1 }.count() == grid.size * grid[0].size) {
                break
            }
            if (i == Int.MAX_VALUE) {
                throw IllegalArgumentException("don't reach to all flash.")
            }
            i += 1
        }
        return i
    }

    val testInput = readInput("day11/Day_test")
    val input = readInput("day11/Day")

    println(part1(testInput))   // confirms 1656
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 195
    println(part2(input))   // part2 answer
}

fun initializeGrid(input: List<String>): MutableList<MutableList<Int>> {
    val grid = mutableListOf<MutableList<Int>>()
    input.forEach { str ->
        val row = mutableListOf<Int>()
        str.forEach {
            row.add(it.digitToInt())
        }
        grid.add(row)
    }
    return grid
}

fun initializeGridExplode(input: List<String>): MutableList<MutableList<Int>> {
    val grid = mutableListOf<MutableList<Int>>()
    input.forEach { str ->
        val row = mutableListOf<Int>()
        str.forEach {
            row.add(0)
        }
        grid.add(row)
    }
    return grid
}

fun printTable(grid: List<List<Int>>) {
    grid.forEach {
        println(it)
    }
}

fun MutableList<MutableList<Int>>.inc(row: Int, col: Int, gridExplode: MutableList<MutableList<Int>>) {
    if (this[row][col] != 9) {
        if (gridExplode[row][col] != 1) {
            this[row][col] += 1
        }
    } else {  // explode
        this[row][col] = 0
        gridExplode[row][col] = 1
        val nRows = this.size
        val nCols = this[0].size
        when (row) {
            0 -> {
                when (col) {
                    0 -> {
                        inc(row, col+1, gridExplode)
                        inc(row+1, col, gridExplode)
                        inc(row+1, col+1, gridExplode)
                    }
                    nCols-1 -> {
                        inc(row, col-1, gridExplode)
                        inc(row+1, col-1, gridExplode)
                        inc(row+1, col, gridExplode)
                    }
                    else -> {
                        inc(row, col-1, gridExplode)
                        inc(row, col+1, gridExplode)
                        inc(row+1, col-1, gridExplode)
                        inc(row+1, col, gridExplode)
                        inc(row+1, col+1, gridExplode)
                    }
                }
            }
            nRows-1 -> {
                when (col) {
                    0 -> {
                        inc(row-1, col, gridExplode)
                        inc(row-1, col+1, gridExplode)
                        inc(row, col+1, gridExplode)
                    }
                    nCols-1 -> {
                        inc(row-1, col-1, gridExplode)
                        inc(row-1, col, gridExplode)
                        inc(row, col-1, gridExplode)
                    }
                    else -> {
                        inc(row-1, col-1, gridExplode)
                        inc(row-1, col, gridExplode)
                        inc(row-1, col+1, gridExplode)
                        inc(row, col-1, gridExplode)
                        inc(row, col+1, gridExplode)
                    }
                }
            }
            else -> {
                when (col) {
                    0 -> {
                        inc(row-1, col, gridExplode)
                        inc(row-1, col+1, gridExplode)
                        inc(row, col+1, gridExplode)
                        inc(row+1, col, gridExplode)
                        inc(row+1, col+1, gridExplode)
                    }
                    nCols-1 -> {
                        inc(row-1, col-1, gridExplode)
                        inc(row-1, col, gridExplode)
                        inc(row, col-1, gridExplode)
                        inc(row+1, col-1, gridExplode)
                        inc(row+1, col, gridExplode)
                    }
                    else -> {
                        inc(row-1, col-1, gridExplode)
                        inc(row-1, col, gridExplode)
                        inc(row-1, col+1, gridExplode)
                        inc(row, col-1, gridExplode)
                        inc(row, col+1, gridExplode)
                        inc(row+1, col-1, gridExplode)
                        inc(row+1, col, gridExplode)
                        inc(row+1, col+1, gridExplode)
                    }
                }
            }
        }
    }
}