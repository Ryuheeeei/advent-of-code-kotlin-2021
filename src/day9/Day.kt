package day9

import readInput

private const val visited = -10
private const val unchecked = -1

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        val grid = convertToGrid(input)
        val nRows = grid.size
        val nCols = grid[0].size

        // 四隅
        // 左上
        if ((grid[0][0] < grid[1][0]) && (grid[0][0] < grid[0][1])) {
            result += grid[0][0] + 1
        } else if ((grid[0][nCols-1] < grid[0][nCols-2]) && (grid[0][nCols-1] < grid[1][nCols-1])) {    // 右上
            result += grid[0][nCols-1] + 1
        } else if ((grid[nRows-1][0] < grid[nRows-2][0]) && (grid[nRows-1][0] < grid[nRows-1][1])) {    // 左下
            result += grid[nRows-1][0] + 1
        } else if ((grid[nRows-1][nCols-1] < grid[nRows-2][nCols-1]) && (grid[nRows-1][nCols-1] < grid[nRows-1][nCols-2])) {
            result += grid[nRows-1][nCols-1] + 1
        }


        for (row in 0 until grid.size) {
            // 上端
            if (row == 0) {
                for (col in 1 until grid[row].size - 1) {
                    if (grid[row][col] < minOf(grid[row][col-1], grid[row][col+1]) && grid[row][col] < grid[row+1][col]) {
                        result += grid[row][col] + 1
                    }
                }
            } else if (row == nRows-1) {    // 下端
                for (col in 1 until grid[row].size - 1) {
                    if (grid[row][col] < minOf(grid[row][col-1], grid[row][col+1]) && grid[row][col] < grid[row-1][col]) {
                        result += grid[row][col] + 1
                    }
                }
            } else {
                // 左端、右端を除く
                for (col in 1 until grid[row].size - 1) {
                    if (grid[row][col] < minOf(grid[row][col-1], grid[row][col+1], grid[row-1][col], grid[row+1][col])) {
                        result += grid[row][col] + 1
                    }
                }
            }
        }
        // 左端
        for (row in 1 until nRows - 1) {
            if (grid[row][0] < minOf(grid[row-1][0], grid[row+1][0]) && grid[row][0] < grid[row][1]) {
                result += grid[row][0] + 1
            }
        }
        // 右端
        for (row in 1 until nRows - 1) {
            if (grid[row][nCols-1] < minOf(grid[row+1][nCols-1], grid[row-1][nCols-1]) && grid[row][nCols-1] < grid[row][nCols-2]) {
                result += grid[row][nCols-1] + 1
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        val grid = convertToGrid(input)
        val gridChecked = convertToCheckedGrid(input)
        val nRows = grid.size
        val nCols = grid[0].size

        // mask 9
        for (row in 0 until nRows) {
            for (col in 0 until nCols) {
                if (grid[row][col] == 9) { gridChecked[row][col] = visited }
            }
        }

        val lowPoints = getLowPoints(grid)
        lowPoints.forEachIndexed { index, pair ->
            val lowRow = pair.first
            val lowCol = pair.second
            gridChecked[lowRow][lowCol] = index
            grid.check(gridChecked, lowRow, lowCol, index)
        }

        var countList = mutableListOf<Int>()
        for (i in lowPoints.indices) {
            val count = gridChecked.flatten().filter { it == i }.count()
            countList.add(count)
        }

        countList.sortDescending()
        return countList[0] * countList[1] * countList[2]
    }

    val testInput = readInput("day9/Day_test")
    val input = readInput("day9/Day")

    println(part1(testInput))   // confirms 15
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 1134
    println(part2(input))   // part2 answer
}

fun convertToGrid(input: List<String>): MutableList<MutableList<Int>> {
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

fun convertToCheckedGrid(input: List<String>): MutableList<MutableList<Int>> {
    val grid = mutableListOf<MutableList<Int>>()
    input.forEach { str ->
        val row = mutableListOf<Int>()
        str.forEach {
            row.add(unchecked)
        }
        grid.add(row)
    }
    return grid
}

fun getLowPoints(grid: MutableList<MutableList<Int>>): MutableList<Pair<Int, Int>> {
    val nRows = grid.size
    val nCols = grid[0].size
    val lowPoints = mutableListOf<Pair<Int, Int>>()

    // 四隅
    // 左上
    if ((grid[0][0] < grid[1][0]) && (grid[0][0] < grid[0][1])) {
        lowPoints.add(Pair(0, 0))
    } else if ((grid[0][nCols-1] < grid[0][nCols-2]) && (grid[0][nCols-1] < grid[1][nCols-1])) {    // 右上
        lowPoints.add(Pair(0, nCols-1))
    } else if ((grid[nRows-1][0] < grid[nRows-2][0]) && (grid[nRows-1][0] < grid[nRows-1][1])) {    // 左下
        lowPoints.add(Pair(nRows-1, 0))
    } else if ((grid[nRows-1][nCols-1] < grid[nRows-2][nCols-1]) && (grid[nRows-1][nCols-1] < grid[nRows-1][nCols-2])) {
        lowPoints.add(Pair(nRows-1, nCols-1))
    }


    for (row in 0 until grid.size) {
        // 上端
        if (row == 0) {
            for (col in 1 until grid[row].size - 1) {
                if (grid[row][col] < minOf(grid[row][col-1], grid[row][col+1]) && grid[row][col] < grid[row+1][col]) {
                    lowPoints.add(Pair(row, col))
                }
            }
        } else if (row == nRows-1) {    // 下端
            for (col in 1 until grid[row].size - 1) {
                if (grid[row][col] < minOf(grid[row][col-1], grid[row][col+1]) && grid[row][col] < grid[row-1][col]) {
                    lowPoints.add(Pair(row, col))
                }
            }
        } else {
            // 左端、右端を除く
            for (col in 1 until grid[row].size - 1) {
                if (grid[row][col] < minOf(grid[row][col-1], grid[row][col+1], grid[row-1][col], grid[row+1][col])) {
                    lowPoints.add(Pair(row, col))
                }
            }
        }
    }
    // 左端
    for (row in 1 until nRows - 1) {
        if (grid[row][0] < minOf(grid[row-1][0], grid[row+1][0]) && grid[row][0] < grid[row][1]) {
            lowPoints.add(Pair(row, 0))
        }
    }
    // 右端
    for (row in 1 until nRows - 1) {
        if (grid[row][nCols-1] < minOf(grid[row+1][nCols-1], grid[row-1][nCols-1]) && grid[row][nCols-1] < grid[row][nCols-2]) {
            lowPoints.add(Pair(row, nCols-1))
        }
    }
    return lowPoints
}


fun MutableList<MutableList<Int>>.check(gridChecked: MutableList<MutableList<Int>>, row: Int, col: Int, number: Int) {
    val nRows = this.size
    val nCols = this[0].size

    if (row == 0) {
        if (col == 0) {
            checkDown(gridChecked, row, col, number)
            checkRight(gridChecked, row, col, number)
        } else if (col == nCols - 1) {
            checkDown(gridChecked, row, col, number)
            checkLeft(gridChecked, row, col, number)
        } else {
            checkDown(gridChecked, row, col, number)
            checkRight(gridChecked, row, col, number)
            checkLeft(gridChecked, row, col, number)
        }
    } else if (row == nRows - 1) {
        if (col == 0) {
            checkUp(gridChecked, row, col, number)
            checkRight(gridChecked, row, col, number)
        } else if (col == nCols - 1) {
            checkUp(gridChecked, row, col, number)
            checkLeft(gridChecked, row, col, number)
        } else {
            checkUp(gridChecked, row, col, number)
            checkLeft(gridChecked, row, col, number)
            checkRight(gridChecked, row, col, number)
        }
    } else if (col == 0) {
        if (row == 0) {
            checkDown(gridChecked, row, col, number)
            checkRight(gridChecked, row, col, number)
        } else if (row == nRows - 1) {
            checkUp(gridChecked, row, col, number)
            checkRight(gridChecked, row, col, number)
        } else {
            checkUp(gridChecked, row, col, number)
            checkDown(gridChecked, row, col, number)
            checkRight(gridChecked, row, col, number)
        }
    } else if (col == nCols - 1) {
        if (row == 0) {
            checkDown(gridChecked, row, col, number)
            checkLeft(gridChecked, row, col, number)
        } else if (row == nRows - 1) {
            checkUp(gridChecked, row, col, number)
            checkLeft(gridChecked, row, col, number)
        } else {
            checkUp(gridChecked, row, col, number)
            checkDown(gridChecked, row, col, number)
            checkLeft(gridChecked, row, col, number)
        }
    } else {
        checkUp(gridChecked, row, col, number)
        checkDown(gridChecked, row, col, number)
        checkLeft(gridChecked, row, col, number)
        checkRight(gridChecked, row, col, number)
    }
}

fun MutableList<MutableList<Int>>.checkUp(gridChecked: MutableList<MutableList<Int>>, row: Int, col: Int, number: Int) {
    if (gridChecked[row - 1][col] != visited) {
        if (gridChecked[row - 1][col] == unchecked) {
            gridChecked[row - 1][col] = number
            check(gridChecked, row - 1, col, number)
        }
    }
}

fun MutableList<MutableList<Int>>.checkDown(gridChecked: MutableList<MutableList<Int>>, row: Int, col: Int, number: Int) {
    if (gridChecked[row + 1][col] != visited) {
        if (gridChecked[row + 1][col] == unchecked) {
            gridChecked[row + 1][col] = number
            check(gridChecked, row + 1, col, number)
        }
    }
}

fun MutableList<MutableList<Int>>.checkLeft(gridChecked: MutableList<MutableList<Int>>, row: Int, col: Int, number: Int) {
    if (gridChecked[row][col - 1] != visited) {
        if (gridChecked[row][col - 1] == unchecked) {
            gridChecked[row][col - 1] = number
            check(gridChecked, row, col - 1, number)
        }
    }
}

fun MutableList<MutableList<Int>>.checkRight(gridChecked: MutableList<MutableList<Int>>, row: Int, col: Int, number: Int) {
    if (gridChecked[row][col + 1] != visited) {
        if (gridChecked[row][col + 1] == unchecked) {
            gridChecked[row][col + 1] = number
            check(gridChecked, row, col + 1, number)
        }
    }
}
