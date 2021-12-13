package day13

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var grid = initializeGrid(input)

        input.filter { it.startsWith("fold") }.take(1).forEach { str ->
            val axis = str.split(" ")[2].split("=")[0][0]
            val point = str.split(" ")[2].split("=")[1].toInt()
            grid = grid.foldAtAxis(axis, point)
            printTable(grid, axis, point)
        }

        return countSharp(grid)
    }

    fun part2(input: List<String>): Int {
        var grid = initializeGrid(input)

        input.filter { it.startsWith("fold") }.forEach { str ->
            val axis = str.split(" ")[2].split("=")[0][0]
            val point = str.split(" ")[2].split("=")[1].toInt()
            grid = grid.foldAtAxis(axis, point)
        }

        grid.forEach {
            println(it)
        }
        return 0
    }

    val testInput = readInput("day13/Day_test")
    val input = readInput("day13/Day")

    println(part1(testInput))   // confirms 17
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms
    println(part2(input))   // part2 answer
}


private fun initializeGrid(input: List<String>): MutableList<MutableList<Char>> {
    val grid = mutableListOf<MutableList<Char>>()
    var initialSizeX = 0
    var initialSizeY = 0
    input.filter { it.contains(",") }.forEach {
        val x = it.split(",")[0].toInt()
        val y = it.split(",")[1].toInt()

        initialSizeX = maxOf(initialSizeX, x + 1)
        initialSizeY = maxOf(initialSizeY, y + 1)
    }

    println(initialSizeX)
    println(initialSizeY)
    for (y in 0 until initialSizeY) {
        val rows = mutableListOf<Char>()
        for (x in 0 until initialSizeX) {
            rows.add(' ')
        }
        grid.add(rows)
    }

    println("grid.X = ${grid[0].size}, grid.Y = ${grid.size}")

    input.filterNot { it.isEmpty() }.filterNot { it.contains("fold") }.forEach { row ->
        val x = row.split(",")[0].toInt()
        val y = row.split(",")[1].toInt()
        grid[y][x] = '#'
    }
    return grid
}

private fun MutableList<MutableList<Char>>.foldAtAxis(axis: Char, point: Int): MutableList<MutableList<Char>> {
    val sizeX = this[0].size
    val sizeY = this.size
    if (axis == 'x') {
        val newGrid = mutableListOf<MutableList<Char>>()
        for (row in 0 until sizeY) {
            val rows = mutableListOf<Char>()
            for (col in 0 until point) {
                rows.add(this[row][col])
            }
            newGrid.add(rows)
        }

        for (col in point-1 downTo 0) {
            for (row in 0 until sizeY) {
                if (this[row][2 * point - col] == '#'){
                    newGrid[row][col] = this[row][2 * point - col]
                }
            }
        }
        return newGrid
    } else {    // folds with y-axis.
        for (row in point-1 downTo 0) {
            for (col in 0 until sizeX) {
                if (this[2 * point - row][col] == '#') {
                    this[row][col] = this[2 * point - row][col]
                }
            }
        }
        for (index in point until sizeY) {
            this.removeAt(point)
        }
        return this
    }
}

private fun printTable(grid: MutableList<MutableList<Char>>, axis: Char, point: Int) {
    grid.forEachIndexed { index, row ->
        if (axis == 'x') {
            println(row)
        } else {
            if (point == index) {
                val indexRow = mutableListOf<Char>()
                for (x in 0 until row.size) {
                    indexRow.add('-')
                }
                println(indexRow)
            } else {
                println(row)
            }
        }
    }
}

private fun countSharp(grid: MutableList<MutableList<Char>>): Int {
    return grid.flatten().count { it == '#' }
}