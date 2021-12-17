package day14

import readInput

fun main() {
    fun part1(input: List<String>, step: Int): Int {
        val initialStr = input.take(1)[0]
        val convertMap = mutableMapOf<String, Char>()

        input.filter { it.contains("->") }.forEach { s ->
            val convertBefore = s.split("->")[0].trim()
            val convertAfter = s.split("->")[1][1]
            convertMap[convertBefore] = convertAfter
        }

        var stepCount = 0
        var state = initialStr

        println(initialStr)
        println(convertMap)

        while(stepCount < step) {
            val insertChar = arrayListOf<Char>()
            for (idx in 0 until state.length-1) {
                val target = listOf(state[idx], state[idx+1]).joinToString("")
                insertChar.add(convertMap[target]!!)
            }
            val stateArr = mutableListOf(state[0])
            val length = state.length - 1
            for (i in 0 until length) {
                stateArr.add(insertChar[i])
                stateArr.add(state[i + 1])
            }
            state = stateArr.joinToString("")
            stepCount += 1
        }

        // TODO: needs refactoring
        val countByChar = mutableMapOf<Char, Int>()
        state.groupBy { it }.forEach { (key, value) -> countByChar[key] = value.size }
        println(countByChar)
        return countByChar.maxByOrNull { it.value }!!.value - countByChar.minByOrNull { it.value }!!.value
    }

    fun part2(input: List<String>, step: Int): Long {
        val convertMap = mutableMapOf<String, Char>()

        input.filter { it.contains("->") }.forEach { s ->
            val convertBefore = s.split("->")[0].trim()
            val convertAfter = s.split("->")[1][1]
            convertMap[convertBefore] = convertAfter
        }

        val initialStr = input.take(1)[0]
        var intermediateStates = mutableListOf<String>()
        var resultList = mutableListOf<MutableMap<Char, Long>>()

        for (i in 0 until initialStr.length-1) {
            var state = initialStr.substring(i..i+1)

            println(state)
            var stepCount = 0

            while(stepCount < step / 2) {
                val insertChar = arrayListOf<Char>()
                for (idx in 0 until state.length-1) {
                    val target = listOf(state[idx], state[idx+1]).joinToString("")
                    insertChar.add(convertMap[target]!!)
                }
                val stateArr = mutableListOf(state[0])
                val length = state.length - 1
                for (j in 0 until length) {
                    stateArr.add(insertChar[j])
                    stateArr.add(state[j + 1])
                }
                state = stateArr.joinToString("")
                stepCount += 1

            }
            intermediateStates.add(state)
        }

        intermediateStates.forEach { s ->
            for (i in 0 until s.length-1) {
                var state = s.substring(i..i+1)
                var stepCount = 0

                while(stepCount < step / 2) {
                    val insertChar = arrayListOf<Char>()
                    for (idx in 0 until state.length-1) {
                         val target = listOf(state[idx], state[idx+1]).joinToString("")
                         insertChar.add(convertMap[target]!!)
                     }
                     val stateArr = mutableListOf(state[0])
                     val length = state.length - 1
                     for (j in 0 until length) {
                         stateArr.add(insertChar[j])
                         stateArr.add(state[j + 1])
                     }
                     state = stateArr.joinToString("")
                     stepCount += 1
                 }

                 // TODO: needs refactoring
                 val countByChar = mutableMapOf<Char, Long>()
                 state.groupBy { it }.forEach { (key, value) -> countByChar[key] = value.toList().size.toLong() }
                 resultList.add(countByChar)
            }
        }

        println(resultList)

        val resultMap = mutableMapOf<Char, Long>()
        resultList.forEach { it ->
            for (key in it.keys) {
                if (resultMap[key] == null) {
                    resultMap.put(key, it[key]!!)
                } else {
                    val temp = resultMap[key]!!
                    resultMap[key] = temp + it[key]!!
                }
            }
        }
        println(resultMap)

        var maxCount = 0L
        var minCount = Long.MAX_VALUE

        resultMap.forEach { t, u ->
            if (u > maxCount) { maxCount = u }
            if (u < minCount) { minCount = u }
        }

        return maxCount - minCount
    }

    val testInput = readInput("day14/Day_test")
    val input = readInput("day14/Day")

    println(part1(testInput, 4))   // confirms 1588
//    println(part1(input, 10))   // part1 answer

    println(part2(testInput, 4))   // confirms 2188189693529
//    println(part2(input))   // part2 answer

}