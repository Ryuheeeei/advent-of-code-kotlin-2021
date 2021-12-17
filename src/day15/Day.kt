package day15

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val n = input.size
        val costArray = arrayListOf<ArrayList<Int>>()
        val totalCostArray = arrayListOf<ArrayList<Int>>()

        input.forEach { row ->
            val temp = arrayListOf<Int>()
            val initials = arrayListOf<Int>()
            row.forEach {
                temp.add(it.digitToInt())
                initials.add(Int.MAX_VALUE)
            }
            costArray.add(temp)
            totalCostArray.add(initials)
        }

        totalCostArray[0][0] = 0
        for (i in 1 until n) {
            totalCostArray[i][0] = totalCostArray[i-1][0] + costArray[i][0]
            totalCostArray[0][i] = totalCostArray[0][i-1] + costArray[0][i]
        }
        return calcTotalCost(totalCostArray, costArray, n-1, n-1)
    }

    fun part2(input: List<String>): Int {
        val n = input.size
        val costArray = arrayListOf<ArrayList<Int>>()
        val totalCostArray = arrayListOf<ArrayList<Int>>()

        input.forEach { row ->
            val temp = arrayListOf<Int>()
            val initials = arrayListOf<Int>()
            row.forEach {
                temp.add(it.digitToInt())
                initials.add(Int.MAX_VALUE)
            }
            costArray.add(temp)
        }

        var extendOriginCostArray = arrayListOf<ArrayList<Int>>()
        for (extendRow in 0 until 5) {
            for (row in 0 until n) {
                val temp = arrayListOf<ArrayList<Int>>()
                for (extendCol in 0 until 5) {
                    val newArray = arrayListOf<Int>()
                    for (col in 0 until n) {
                        newArray.add(costArray[row][col] + extendRow + extendCol)
                    }
                    temp.add(newArray)
                }
                extendOriginCostArray.add(temp.flatten() as ArrayList<Int>)
            }
        }

        println("---Before Convert---")
        println(extendOriginCostArray[0])

        val extendCostArray = convertToNineAry(extendOriginCostArray)
        println("---After Convert---")

        println("---test---")
        println(extendCostArray[0])

        for (i in 0 until n * 5) {
            val temp = arrayListOf<Int>()
            for (j in 0 until n * 5) {
                temp.add(Int.MAX_VALUE)
            }
            totalCostArray.add(temp)
        }

        totalCostArray[0][0] = 0
        for (i in 1 until n * 5) {
            totalCostArray[i][0] = totalCostArray[i-1][0] + extendCostArray[i][0]
        }
        for (j in 1 until n * 5) {
            totalCostArray[0][j] = totalCostArray[0][j-1] + extendCostArray[0][j]
        }

        println("---hello---")
        println(extendCostArray[0])
        println(totalCostArray[0])

        for (i in 1 until n * 5) {
            for (j in 1 until n * 5) {
                totalCostArray[i][j] = calcTotalCostBySum(totalCostArray, extendCostArray, i, j)
            }
        }
        return calcTotalCost(totalCostArray, extendCostArray, n*5-1, n*5-1)
    }

    val testInput = readInput("day15/Day_test")
    val input = readInput("day15/Day")

    println(part1(testInput))   // confirms 40
    println(part1(input))   // part1 answer

    println(part2(testInput))   // confirms 315
    println(part2(input))   // TODO: incorrect answer part2 answer 2990 (too high)
}

private fun printArray(grid: ArrayList<ArrayList<Int>>) {
    grid.forEach {
        println(it)
    }
}

private fun calcTotalCost(
    totalCostArray: ArrayList<ArrayList<Int>>,
    costArray: ArrayList<ArrayList<Int>>,
    row: Int, col: Int): Int
{
    return if (row == 0) {
        totalCostArray[0][col]
    } else if (col == 0) {
        totalCostArray[row][0]
    } else if (totalCostArray[row][col] != Int.MAX_VALUE) {
        totalCostArray[row][col]
    } else {
        totalCostArray[row][col] = minOf(
            calcTotalCost(totalCostArray, costArray, row - 1, col) + costArray[row][col],
            calcTotalCost(totalCostArray, costArray, row, col - 1) + costArray[row][col]
        )
        minOf(
            calcTotalCost(totalCostArray, costArray, row - 1, col) + costArray[row][col],
            calcTotalCost(totalCostArray, costArray, row, col - 1) + costArray[row][col]
        )
    }
}

private fun calcTotalCostBySum(
    totalCostArray: ArrayList<ArrayList<Int>>,
    costArray: ArrayList<ArrayList<Int>>,
    row: Int, col: Int): Int
{
    return minOf(totalCostArray[row-1][col] + costArray[row][col],
        totalCostArray[row][col-1] + costArray[row][col])
}

private fun convertToNineAry(grid: ArrayList<ArrayList<Int>>): ArrayList<ArrayList<Int>> {
    for (row in 0 until grid.size) {
        for (col in 0 until grid[0].size) {
            if (grid[row][col] > 9) {
                grid[row][col] = grid[row][col] - 9
            }
        }
    }
    return grid
}
