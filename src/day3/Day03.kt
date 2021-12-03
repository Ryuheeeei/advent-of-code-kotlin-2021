package day3

import readInput

// TODO: implement test and refactor
fun main() {
    fun part1(input: List<String>): Int {
        val calcResult = calcCommonArray(input)

        val gammaRate = calcResult[0].joinToString(separator = "").toInt(2)
        val epsilonRate = calcResult[1].joinToString(separator = "").toInt(2)

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val nDigits = input[0].length
        var filtered = input

        for (idx in 0 until nDigits) {
            val (ones, zeros) = filtered.countBits(idx)
            filtered = filtered.filter { s: String ->
                    val mostCommon = if (ones >= zeros) '1' else '0'
                    s[idx] == mostCommon
            }
            if(filtered.size == 1) break
        }
        val oxygenGeneratorRate = filtered[0].toInt(2)

        filtered = input
        for (idx in 0 until nDigits) {
            val (ones, zeros) = filtered.countBits(idx)
            filtered = filtered.filter { s: String ->
                val leastCommon = if (ones >= zeros) '0' else '1'
                s[idx] == leastCommon
            }
            if(filtered.size == 1) break
        }
        val co2ScrubberRate = filtered[0].toInt(2)
        return oxygenGeneratorRate * co2ScrubberRate
    }

    val testInput = readInput("day3/Day03_test")
    val input = readInput("day3/Day03")

    println(part1(testInput))   // confirms 198
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 230
    println(part2(input))   // part2 answer
}

/**
 * get binary string list, return most common array and least common array
 */
private fun calcCommonArray(input: List<String>): List<MutableList<Int>> {
    val arrayOfZero = mutableListOf<Int>()
    val arrayOfOne = mutableListOf<Int>()
    val gammaArray = mutableListOf<Int>()
    val epsilonArray = mutableListOf<Int>()

    val nDigits = input[0].length

    for (i in 0 until nDigits) {
        arrayOfOne.add(1)
        arrayOfZero.add(0)
        gammaArray.add(1)
        epsilonArray.add(0)
    }

    input.forEach { str ->
        for ((idx, value) in str.withIndex()) {
            when (value) {
                '1' -> arrayOfOne[idx] += 1
                '0' -> arrayOfZero[idx] += 1
            }
        }
    }

    for (i in 0 until nDigits) {
        if (arrayOfOne[i] > arrayOfZero[i]) {
            gammaArray[i] = 1
            epsilonArray[i] = 0
        } else {
            gammaArray[i] = 0
            epsilonArray[i] = 1
        }
    }
    return listOf(gammaArray, epsilonArray)
}

private fun List<String>.countBits(index: Int): Pair<Int, Int> {
    var ones = 0
    var zeros = 0
    this.forEach {
        when(it[index]) {
            '0' -> { zeros += 1 }
            '1' -> { ones += 1}
        }
    }
    return Pair(ones, zeros)
}