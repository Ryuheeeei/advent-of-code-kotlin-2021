package day7

import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var crabs = input[0].split(",").map { it.trim().toInt() }.sorted()
        val minCrab = crabs[0]
        val maxCrab = crabs[crabs.size-1]
        var result = Int.MAX_VALUE

        for (x in minCrab..maxCrab) {
            var temp = 0
            for (element in crabs) {
                temp += abs(element - x)
            }
            if (temp < result) {
                result = temp
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var crabs = input[0].split(",").map { it.trim().toInt() }.sorted()
        val minCrab = crabs[0]
        val maxCrab = crabs[crabs.size-1]
        var result = Int.MAX_VALUE

        for (x in minCrab..maxCrab) {
            var temp = 0
            for (element in crabs) {
                temp += sum(abs(element - x))
            }
            if (temp < result) {
                result = temp
            }
        }

        return result
    }

    val testInput = readInput("day7/Day_test")
    val input = readInput("day7/Day")

    println(part1(testInput))   // confirms 37
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 168
    println(part2(input))   // part2 answer
}

fun sum(n: Int): Int = n * (n + 1) / 2