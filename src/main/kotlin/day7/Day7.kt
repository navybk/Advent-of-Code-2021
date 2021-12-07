package day7

import base.FileHandling
import base.expect
import kotlin.math.abs
import kotlin.math.min

fun main() {
    Day7()
}

class Day7 {

    init {
        getInput("test.txt").resolveFirst().expect(37)
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect(168)
        getInput("input.txt").resolveSecond()
    }

    private fun Map<Int, Int>.resolveFirst(): Int {
        val maxNum = this.maxOf { it.key }
        val minNum = this.minOf { it.key }

        var sum = Int.MAX_VALUE

        for (index in minNum..maxNum) {
            sum = map {
                abs(it.key - index) * it.value
            }.let {
                min(it.sum(), sum)
            }
        }
        println("result: $sum")
        return sum
    }

    private fun Map<Int, Int>.resolveSecond(): Int {
        val maxNum = this.maxOf { it.key }
        val minNum = this.minOf { it.key }

        val sumCache = mutableMapOf<Int, Int>()

        var sum = Int.MAX_VALUE

        for (index in minNum..maxNum) {
            sum = map {
                val distance = abs(it.key - index)
                val costs = sumCache.getOrPut(distance) { distance.gaussSummation() }
                costs * it.value
            }.let {
                min(it.sum(), sum)
            }
        }
        println("result: $sum")
        return sum
    }

    private fun Int.gaussSummation() =
        (this * (this + 1)) / 2

    private fun getInput(filename: String): Map<Int, Int> =
        FileHandling("/day7/$filename").map { line ->
            line.split(",").map {
                it.toInt()
            }
        }.first()
            .groupingBy { it }
            .eachCount()
}