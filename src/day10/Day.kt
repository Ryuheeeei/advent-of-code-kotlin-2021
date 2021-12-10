package day10

import readInput

private val parenthesisMap = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
private val illegalPointMap = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
private val incompletePointMap = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0

        input.forEach { line ->
            val parenthesisQueue = mutableListOf<Char>()
            var isValid = true
            line.forEach { ch ->
                if (isValid) {
                    when (ch) {
                        '(', '[', '{', '<' -> parenthesisQueue.add(ch)
                        else -> {
                            val correspondChar = parenthesisQueue.removeLast()
                            if (parenthesisMap[ch] != correspondChar) {     // corrupted
                                result += illegalPointMap[ch]!!
                                isValid = false
                            }
                        }
                    }
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Long {
        val resultList = mutableListOf<Long>()

        input.forEach { line ->
            val parenthesisQueue = mutableListOf<Char>()
            var isValid = true
            var lineScore = 0L
            line.forEach { ch ->
                if (isValid) {
                    when (ch) {
                        '(', '[', '{', '<' -> parenthesisQueue.add(ch)
                        else -> {
                            val correspondChar = parenthesisQueue.removeLast()
                            if (parenthesisMap[ch] != correspondChar) {     // corrupted
                                isValid = false
                            }
                        }
                    }
                }
            }

            if (isValid) {  // incomplete
                parenthesisQueue.reverse()
                parenthesisQueue.forEach { parenthesis ->
                    incompletePointMap[parenthesis]?.let {
                        lineScore = lineScore * 5 + it
                    }
                }
                resultList.add(lineScore)
            }
        }

        resultList.sort()
        val halfIndex = resultList.size / 2
        println("size = ${resultList.size}, halfIndex = $halfIndex")
        return resultList[halfIndex]
    }

    val testInput = readInput("day10/Day_test")
    val input = readInput("day10/Day")

    println(part1(testInput))   // confirms 26397
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 288957
    println(part2(input))   // part2 answer
}
