package day8

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { sentence ->
            val output = sentence.split("|")[1].trim()
            val numbers = output.split(" ")
            numbers.forEach {
                when(it.length) {
                    2, 3, 4, 7 -> result += 1
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        input.forEach { sentence ->
            val inputStr = sentence.split("|")[0].trim()
            val outputStr = sentence.split("|")[1].trim()
            /*
            *  TTTT
            * U    V
            * U    V
            *  WWWW
            * X    Y
            * X    Y
            *  ZZZZ
            *
            * T: 8 U: 6 V: 8 W: 7 X: 4 Y: 9 Z: 7
            * */
            val countNumbers = mutableMapOf('a' to 0, 'b' to 0, 'c' to 0, 'd' to 0, 'e' to 0, 'f' to 0, 'g' to 0)
            val inputStrForCount = inputStr.split(" ").joinToString("", "")
            for (c in countNumbers.keys) {
                countNumbers[c] = inputStrForCount.count { it == c }
            }

            // u, x, y -> inferred by char count
            val u = countNumbers.filter { it.value == 6 }.keys.first()
            val x = countNumbers.filter { it.value == 4 }.keys.first()
            val y = countNumbers.filter { it.value == 9 }.keys.first()
            val one = inputStr.split(" ").filter { s: String -> s.length == 2 }
            val v = one[0].filter { it != y }.first()
            // t -> 8 chars and not v
            val t = countNumbers.filter { it.value == 8 }.filter { it.key != v }.keys.first()
            // w -> number: 4 and not u
            val four = inputStr.split(" ").filter { s: String -> s.length == 4 }
            val w = four[0].filter { it != u }.filter { it != v }.filter { it != y }.first()
            // z -> 7 chars and not w
            val z = countNumbers.filter { it.value == 7 }.filter { it.key != w}.keys.first()

            val outputList = mutableListOf(0, 0, 0, 0)
            val converter = Converter(t, u, v, w, x, y, z)
            outputStr.split(" ").forEachIndexed { index, s ->
                outputList[index] = converter.toNumber(s)
            }
            result += outputList.joinToString("").toInt()
        }
        return result
    }

    val testInput = readInput("day8/Day_test")
    val input = readInput("day8/Day")

    println(part1(testInput))   // confirms 26
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 61229
    println(part2(input))   // part2 answer
}

private fun logic(index: Int, s: String, numberAlphabets: MutableList<String>, numberList: MutableList<Int>): Pair<MutableList<String>, MutableList<Int>>{
    when(s.length) {
        2 -> {
            numberAlphabets[1] = s
            numberList[index] = 1
        }
        3 -> {
            numberAlphabets[7] = s
            numberList[index] = 7
        }
        4 -> {
            numberAlphabets[4] = s
            numberList[index] = 4
        }
        7 -> {
            numberAlphabets[8] = s
            numberList[index] = 8
        }
    }
    return Pair(numberAlphabets, numberList)
}

class Converter(val t: Char, val u: Char, val v: Char, val w:Char, val x: Char, val y: Char, val z: Char) {
    fun toNumber(str: String): Int {
        val sortedStr = str.split("").filter { it != "" }.sorted().joinToString("")
        val answer = when(sortedStr) {
            listOf(t, u, x, v, y, z).convertUtil()-> 0
            listOf(v, y).convertUtil() -> 1
            listOf(t, v, w, x, z).convertUtil() -> 2
            listOf(t, v, w, y, z).convertUtil() -> 3
            listOf(u, w, v, y).convertUtil() -> 4
            listOf(t, u, w, y, z).convertUtil() -> 5
            listOf(t, u, w, x, y, z).convertUtil() -> 6
            listOf(t, v, y).convertUtil() -> 7
            listOf(t, u, v, w, x, y, z).convertUtil() -> 8
            listOf(t, u, v, w, y, z).convertUtil() -> 9
            else -> throw IllegalArgumentException("Unknown number")
        }
        return answer
    }

    private fun List<Char>.convertUtil(): String = this.sorted().joinToString("")
}