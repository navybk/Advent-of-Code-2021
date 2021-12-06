package day6

import base.FileHandling
import base.expect
import java.util.Collections

fun main() {
    Day6()
}

class Day6 {

    init {
        getInput("test.txt").resolveFirst().expect(5934)
        getInput("input.txt").resolveFirst()

        getInput("test.txt").resolveSecond().expect(26984457539)
        getInput("input.txt").resolveSecond()
    }

    private fun List<Long>.resolveFirst(): Long {
        return resolveForDays(80).also {
            println("result: $it")
        }
    }

    private fun List<Long>.resolveSecond(): Long {
        return resolveForDays(256).also {
            println("result: $it")
        }
    }

    private fun List<Long>.resolveForDays(days: Int): Long {
        val list = this.groupingBy {
            it
        }.eachCount()
            .let { list ->
                MutableList(7) {
                    list.getOrElse(it.toLong()) {
                        0L
                    }.toLong()
                }
            }

        var newList = List(2) { 0L }

        repeat(days) {
            val new = list[0]
            Collections.rotate(list, -1)
            list[6] = list[6] + newList[0]
            newList = listOf(newList[1], new)
        }
        return list.sum() + newList.sum()
    }

    private fun getInput(filename: String): List<Long> =
        FileHandling("/day6/$filename").map { line ->
            line.split(",")
                .mapNotNull { it.toLongOrNull() }
        }.first()
}
