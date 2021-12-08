package day8

import base.FileHandling
import base.expect
import java.lang.IllegalStateException

typealias DigitFields = List<Pair<List<String>, List<String>>>

fun main() {
    Day8()
}

class Day8 {

    init {
        getInput("test.txt").resolveFirst().expect(26)
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect(61229)
        getInput("input.txt").resolveSecond()
    }

    private fun DigitFields.resolveFirst(): Int {
        val lengthList = lengthMap.values
        return sumOf { pair ->
            pair.second.count { value ->
                value.length in lengthList
            }
        }.also {
            println("result: $it")
        }
    }

    private fun DigitFields.resolveSecond(): Int = sumOf { pair ->
        val knownMap = pair.first.mapNotNull { number ->
            lengthMap.filterValues { it == number.length }
                .keys
                .firstOrNull()
                ?.let {
                    number to it
                }
        }.toMap()

        pair.second.map { digitNumber ->
            knownMap[digitNumber] ?: digitNumber.deductNumber(knownMap)
        }.joinToString("")
            .toInt()
    }.also {
            println("result: $it")
        }

    private fun getInput(filename: String): DigitFields =
        FileHandling("/day8/$filename").map { line ->
            line.split(" | ")
                .map {
                    it.split(" ").map { digits ->
                        digits.toCharArray().sorted().joinToString("")
                    }
                }.let {
                    it[0] to it[1]
                }
        }

    private fun String.deductNumber(knownDigits: Map<String, Int>): Int =
        knownDigits.map { (digitFields, _) ->
            filterNot {
                digitFields.indexOf(it) > -1
            }.length
        }.sum()
            .let {
                when (it) {
                    10 -> if (length == 5) {
                        2
                    } else {
                        0
                    }

                    7 -> 3

                    9 -> if (length == 5) {
                        5
                    } else {
                        9
                    }

                    12 -> 6

                    else -> throw IllegalStateException()
                }
            }

    companion object {
        // map of digits and unique count of segments
        private val lengthMap = mapOf(1 to 2, 4 to 4, 7 to 3, 8 to 7)
    }
}

