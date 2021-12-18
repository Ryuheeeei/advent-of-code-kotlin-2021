package day17

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val targetX = input[0].split(",")[0]
        val targetY = input[0].split(",")[1]
        val targetMinX = targetX.split("..")[0].filter { it.isDigit() }.toInt()
        val targetMaxX = targetX.split("..")[1].toInt()
        val targetMinY = targetY.split("..")[0].split("y=")[1].toInt()
        val targetMaxY = targetY.split("..")[1].toInt()
        println("x: $targetMinX .. $targetMaxX, y: $targetMinY .. $targetMaxY")

        var step = 0
        var count = 0
        var pos = mutableListOf(0, 0)
        val posVisitedY = mutableListOf(pos[1])

        for (initialVelocityX in 0..targetMaxX) {
            for (initialVelocityY in targetMinY..1000) {    // TODO: How to calculate this limit...
                step = 0
                pos = mutableListOf(0, 0)
                var isReached = false
                var velocity = mutableListOf(initialVelocityX, initialVelocityY)
                while(true) {
                    step += 1
                    val result = calcNextStep(pos, velocity, step)
                    pos = result[0]
                    velocity = result[1]
                    if (isInTargetArea(pos, targetMinX, targetMaxX, targetMinY, targetMaxY)) {
                        isReached = true
                        count += 1
                        println("Reached at $step times, pos = $pos")
                        break
                    } else if (pos[0] > targetMaxX || pos[1] < targetMinY) {
                        break
                    }
                }

                if (isReached) {
                    pos = mutableListOf(0, 0)
                    velocity = mutableListOf(initialVelocityX, initialVelocityY)
                    for (s in 0..step) {
                        val result = calcNextStep(pos, velocity, step)
                        pos = result[0]
                        velocity = result[1]
                        posVisitedY.add(pos[1])
                    }
                }

            }
        }
        println("count of initial velocity that reaches target area = $count")
        return posVisitedY.maxOf { it }
    }

    fun part2(input: List<String>): Int {
        return part1(input)
    }

    val testInput = readInput("day17/Day_test")
    val input = readInput("day17/Day")

    println(part1(testInput))   // confirms 45
    println(part1(input))   // part1 answer
}

fun calcNextStep(pos: MutableList<Int>, velocity: MutableList<Int>, step: Int): List<MutableList<Int>> {
    val newPosition = mutableListOf(pos[0] + velocity[0], pos[1] + velocity[1])

    val newVelocity = mutableListOf(0, velocity[1] - 1)
    newVelocity[0] = when {
        velocity[0] > 0 -> velocity[0] - 1
        velocity[0] < 0 -> velocity[0] + 1
        else -> 0
    }

    return listOf(newPosition, newVelocity)
}

fun isInTargetArea(pos: MutableList<Int>, targetMinX: Int, targetMaxX: Int, targetMinY: Int, targetMaxY: Int): Boolean {
    return pos[0] in targetMinX..targetMaxX && pos[1] in targetMinY..targetMaxY
}
