package day4

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val callNumbers = input[0].split(",").map { it.toInt() }
        val bingoBoards = parseInputToBingo(input)
        val nBingos = bingoBoards.size
        for (number in callNumbers) {
            for (j in 0 until nBingos) {
                val point: Pair<Int, Int> = bingoBoards[j].hasNumber(number) ?: continue
                bingoBoards[j].setMarked(point.first, point.second)
                if (bingoBoards[j].checkBingo()) {
                    val otherRemained = bingoBoards[j].calcRemained()
                    val numberCalled = number.toString().toInt()
                    return otherRemained * numberCalled
                }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val callNumbers = input[0].split(",").map { it.toInt() }
        val bingoBoards = parseInputToBingo(input)
        val nBingos = bingoBoards.size
        val finishedBoards = mutableListOf<Int>()
        for (i in 0 until nBingos) {
            finishedBoards.add(0)
        }

        for (number in callNumbers) {
            for (j in 0 until nBingos) {
                val point: Pair<Int, Int> = bingoBoards[j].hasNumber(number) ?: continue
                bingoBoards[j].setMarked(point.first, point.second)
                if (bingoBoards[j].checkBingo()) {
                    finishedBoards[j] = 1
                    if (finishedBoards.sum() == nBingos) {
                        val otherRemained = bingoBoards[j].calcRemained()
                        val numberCalled = number.toString().toInt()
                        return otherRemained * numberCalled
                    }
                }
            }
        }
        return 0
    }

    val testInput = readInput("day4/Day04_test")
    val input = readInput("day4/Day04")

    println(part1(testInput))   // confirms 4512
    println(part1(input))   // part1 answer X: 27027 (Too low)

    // Test
//    val testBoard = BingoBoard(
//        listOf(
//            listOf(0, 0, 0, 0, 0),
//            listOf(0, 0, 0, 0, 0),
//            listOf(0, 0, 0, 0, 0),
//            listOf(0, 0, 0, 0, 0),
//            listOf(0, 0, 0, 0, 0),
//        )
//    )
//    for (row in 0 until 5) {
//        testBoard.setMarked(4-row, row)
//    }
//    println(testBoard.checkBingo())

    println(part2(testInput))   // confirms 1924
    println(part2(input))   // part2 answer
}

class BingoBoard(
    private val board: List<List<Int>>,
) {
    private var marked = mutableListOf<MutableList<Int>>(
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0),
    )
    private val size = 5


    fun hasNumber(number: Int): Pair<Int, Int>? {
        for (row in 0 until size) {
            for (col in 0 until size) {
                if (board[row][col] == number) {
                    return Pair(row, col)
                }
            }
        }
        return null
    }

    fun setMarked(row: Int, col: Int) {
        marked[row][col] = 1
    }

    fun checkBingo(): Boolean {
        // Row
        for (row in 0 until size) {
            if (marked[row].sum() == 5) return true
        }

        // Column
        for (col in 0 until size) {
            var colSum = 0
            for (row in 0 until size) {
                colSum += marked[row][col]
            }
            if (colSum == 5) return true
        }
        return false
    }

    fun calcRemained(): Int {
        var result = 0
        for (row in 0 until size) {
            for (col in 0 until size) {
                if (marked[row][col] == 0) result += board[row][col]
            }
        }
        return result
    }
}

fun parseInputToBingo(input: List<String>): MutableList<BingoBoard> {

    val bingoBoards = mutableListOf<BingoBoard>()
    val bingoLines = input.filterIndexed { idx, _ -> idx != 0}.filter { it.isNotEmpty() }

    bingoLines.chunked(5).forEach { oneCard ->
        val rows: MutableList<List<Int>> = mutableListOf()
        val temp: MutableList<List<String>> = mutableListOf()
        oneCard.forEach { row ->
            temp.add(row.split(","))
        }
        temp.forEach { rows.add(it[0].split(" ")
            .filter { element ->  element.isNotEmpty() }.map { it.toInt() }) }
        val bingoBoard = BingoBoard(rows)
        bingoBoards.add(bingoBoard)
    }
    return bingoBoards
}
