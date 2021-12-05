package day5

import readInput
import kotlin.math.abs

const val size = 1000

fun main() {
    fun part1(input: List<String>): Int {
        val grid = initialize(size)
        val filtered = input.filter { line ->
            val result = line.split("-> ")
            val (x1, y1) = result[0].split(",").map { it.trim().toInt() }
            val (x2, y2) = result[1].split(",").map { it.trim().toInt() }
            x1 == x2 || y1 == y2
        }

        for (condition in filtered) {
            val result = condition.split("-> ")
            val (x1, y1) = result[0].split(",").map { it.trim().toInt() }
            val (x2, y2) = result[1].split(",").map { it.trim().toInt() }
            if (x1 == x2) {
                val minY = minOf(y1, y2)
                val maxY = maxOf(y1, y2)
                for (y in minY..maxY) {
                    grid[y][x1] += 1
                }
            } else if (y1 == y2) {
                val minX = minOf(x1, x2)
                val maxX = maxOf(x1, x2)
                for (x in minX..maxX) {
                    grid[y1][x] += 1
                }
            }
        }
        return countDuplicates(grid)
    }

    fun part2(input: List<String>): Int {
        val grid = initialize(size)
        val filtered = input.filter { line ->
            val result = line.split("-> ")
            val (x1, y1) = result[0].split(",").map { it.trim().toInt() }
            val (x2, y2) = result[1].split(",").map { it.trim().toInt() }
            x1 == x2 || y1 == y2 || abs(x1 - x2) == abs(y1 - y2)
        }

        for (condition in filtered) {
            val result = condition.split("-> ")
            val (x1, y1) = result[0].split(",").map { it.trim().toInt() }
            val (x2, y2) = result[1].split(",").map { it.trim().toInt() }
            if (x1 == x2) {
                val minY = minOf(y1, y2)
                val maxY = maxOf(y1, y2)
                for (y in minY..maxY) {
                    grid[y][x1] += 1
                }
            } else if (y1 == y2) {
                val minX = minOf(x1, x2)
                val maxX = maxOf(x1, x2)
                for (x in minX..maxX) {
                    grid[y1][x] += 1
                }
            } else {
                // diagonal case
                val length = abs(x2 - x1)
                val isLeftDownLine = if ((x1 < x2 && y1 < y2) || (x1 > x2) && (y1 > y2)) 1 else 0

                if (isLeftDownLine == 1) {
                    val minX = minOf(x1, x2)
                    val minY = minOf(y1, y2)
                    for (i in 0..length) {
                        grid[minY + i][minX + i] += 1
                    }
                } else {
                    val minY = minOf(y1, y2)
                    val maxX = maxOf(x1, x2)
                    for (i in 0..length) {
                        grid[minY + i][maxX - i] += 1
                    }
                }
            }
        }
        return countDuplicates(grid)
    }

    val testInput = readInput("day5/Day05_test")
    val input = readInput("day5/Day05")

    println(part1(testInput))   // confirms 5
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 12
    println(part2(input))   // part2 answer
}

private fun initialize(size: Int): MutableList<MutableList<Int>> {
    val grid = mutableListOf<MutableList<Int>>()
    for (y in 0 until size) {
        val row = mutableListOf<Int>()
        for (x in 0 until size) {
            row.add(0)
        }
        grid.add(row)
    }
    return grid
}

private fun countDuplicates(grid: MutableList<MutableList<Int>>): Int {
    var result = 0
    for (row in grid) {
        for (col in 0 until grid[0].size) {
            if (row[col] >= 2) {
                result += 1
            }
        }
    }
    return result
}