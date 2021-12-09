package day9

import base.FileHandling
import base.expect

typealias IntMap = Map<Pair<Int, Int>, Int>

fun main() {
    Day9()
}

class Day9 {

    init {
        getInput("test.txt").resolveFirst().expect(15)
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect(1134)
        getInput("input.txt").resolveSecond()
    }

    private fun IntMap.resolveFirst(): Int =
        lowestPoints()
            .map { (_, value) ->
                value + 1
            }.sum()
            .also {
                println("result: $it")
            }

    private fun IntMap.resolveSecond(): Int =
        lowestPoints()
            .asSequence()
            .map { (initKey, _) ->
                val area = mutableSetOf(initKey)
                var tempArea = listOf(initKey)
                while (tempArea.isNotEmpty()) {
                    tempArea = tempArea.mapNotNull { position ->
                        val value = this[position] ?: return@mapNotNull null
                        listOfNotNull(
                            getPoint(position.first - 1, position.second, value),
                            getPoint(position.first + 1, position.second, value),
                            getPoint(position.first, position.second - 1, value),
                            getPoint(position.first, position.second + 1, value),
                        ).also {
                            area += it
                        }
                    }.flatten()
                }
                area.size
            }.sortedDescending()
            .take(3)
            .reduce(Int::times)
            .also {
                println("result: $it")
            }

    private fun IntMap.getPoint(x: Int, y: Int, value: Int) =
        this[x to y]?.takeIf {
            it in (value + 1)..8
        }?.let {
            x to y
        }

    private fun IntMap.lowestPoints(): IntMap =
        filter { (key, value) ->
            value < 9 &&
                    (this[key.first to (key.second - 1)] ?: Int.MAX_VALUE) > value &&
                    (this[(key.first - 1) to key.second] ?: Int.MAX_VALUE) > value &&
                    (this[(key.first + 1) to key.second] ?: Int.MAX_VALUE) > value &&
                    (this[key.first to (key.second + 1)] ?: Int.MAX_VALUE) > value
        }

    private fun getInput(filename: String): IntMap {
        var rowIndex = 0
        return FileHandling("/day9/$filename").map { line ->
            line.mapIndexed { index, value ->
                Pair(index, rowIndex) to Character.getNumericValue(value)
            }.also {
                rowIndex++
            }
        }.flatten()
            .toMap()
    }
}