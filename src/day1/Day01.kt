package day1

import readInput
import toInt

fun main() {
    fun part1(input: List<Int>): Int {
        var result = 0
        var aheadValue = input[0].toInt()

        input.forEach { it ->
            val thisValue = it.toInt()
            if (aheadValue < thisValue) {
                result++
            }
            aheadValue = thisValue
        }
        return result
    }

    fun part2(input: List<Int>): Int {
        var result = 0
        val calcResultList = mutableListOf<Int>()

        for (i in 2 until input.size) {
            calcResultList.add(input[i-2] + input[i-1] + input[i])
        }

        result = part1(calcResultList)

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day1/Day01_test")
    val input = readInput("day1/Day01")
    println(part1(testInput.toInt()))   // confirms 7
    println(part1(input.toInt()))   // part1 answer

    println(part2(testInput.toInt()))   // confirms 5
    println(part2(input.toInt()))   // part2 answer
}
