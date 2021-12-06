package day6

import readInput

fun main() {
    fun part1(input: List<String>, days: Int = 80): Int {
        val initialLanterns = input[0].split(",").map { it.trim().toInt() }
        var day = 0
        var lanterns = mutableListOf<Int>()
        lanterns = initialLanterns as MutableList<Int>

        while (day < days) {
            var addLantern = 0
            lanterns.forEachIndexed { index, lantern ->
                when(lantern) {
                    0 -> {
                        lanterns[index] = 6
                        addLantern += 1
                    }
                    else -> {
                        lanterns[index] -= 1
                    }
                }
            }

            for (newLantern in 0 until addLantern) {
                lanterns.add(8)
            }
            day += 1
        }
        return lanterns.size
    }

    fun part2(input: List<String>, days: Int = 5): Long {
        // part1's strategy causes heap error, need another strategy
        // return part1(input, days)

        val initialLanterns = input[0].split(",").map { it.trim().toInt() }
        var day = 0
        var nLanternsList = mutableListOf<Long>(0, 0, 0, 0, 0, 0, 0, 0, 0)

        // pair of (lifetime, count)
        for (i in 0..8) {
            val count: Long = initialLanterns.filter { it == i }.size.toLong()
            nLanternsList[i] = count
        }

        while (day  < days) {
            var bornThatDay: Long = 0
            nLanternsList.forEachIndexed { idx, count ->
                when (idx) {
                    0 -> {
                        bornThatDay = count
                        nLanternsList[0] = 0
                    }
                    else -> {
                        nLanternsList[idx - 1] += count
                        nLanternsList[idx] = 0
                    }
                }
            }
            nLanternsList[6] += bornThatDay
            nLanternsList[8] += bornThatDay
            day += 1
        }
        return nLanternsList.sum()
    }

    val testInput = readInput("day6/Day06_test")
    val input = readInput("day6/Day06")

    println(part1(testInput, 18))   // confirms 26
    println(part1(testInput))   // confirms 5934
    println(part1(input))   // part1 answer
    println(part2(testInput, 256))   // confirms 26,984,457,539
    println(part2(input, 256))   // part2 answer
}
