package day9

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        val grid = convertToGrid(input)
        val nRows = grid.size
        val nCols = grid[0].size

        println(grid)

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

        val lowPoints = listOf(Pair(0, 1), Pair(0, 9), Pair(2, 3), Pair(4, 6))

        for (lowPoint in lowPoints) {
            val lowRow = lowPoint.first
            val lowCol = lowPoint.second
        }

        return result
    }

    val testInput = readInput("day9/Day_test")
    val input = readInput("day9/Day")

    println(part1(testInput))   // confirms 15
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 1134
//    println(part2(input))   // part2 answer
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

fun MutableList<MutableList<Int>>.check(row: Int, col: Int) {
    if (row == 0 && col == 0) {
        check(row+1, col)
        check(row, col+1)
    } else if (row == this.size - 1 && col == 0) {
        check(row-1, col)
        check(row, col+1)
    } else if (row == 0 && col == this[0].size - 1) {
        check(row+1, col)
        check(row, col-1)
    } else if (row == this.size - 1 && col == this[0].size -1) {
        check(row-1, col)
        check(row, col-1)
    }
    if (row == 0) {
        check(row, col-1)
        check(row, col+1)
        check(row+1, col)
        return
    } else if ()
}