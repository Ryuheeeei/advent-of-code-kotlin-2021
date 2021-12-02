package day2

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var horizontalPos = 0
        var depth = 0

        input.forEach { s ->
            val (direction, units) = s.split(" ")
            when (direction) {
                "forward" -> horizontalPos += units.toInt()
                "down" -> depth += units.toInt()
                "up" -> depth -= units.toInt()
            }
        }
        return horizontalPos * depth
    }

    fun part2(input: List<String>): Int {
        var horizontalPos = 0
        var depth = 0
        var aim = 0

        input.forEach { s ->
            val (direction, units) = s.split(" ")
            when (direction) {
                "forward" -> {
                    horizontalPos += units.toInt()
                    depth += aim * units.toInt()
                }
                "down" -> aim += units.toInt()
                "up" -> aim -= units.toInt()
            }
        }

        return horizontalPos * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day2/Day02_test")
    val input = readInput("day2/Day02")
    println(part1(testInput))   // confirms 150
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 900
    println(part2(input))   // part2 answer
}
