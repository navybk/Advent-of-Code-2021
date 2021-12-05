package day1

import base.FileHandling
import base.expect

fun main() {
    Day1()
}

class Day1 {

    init {
        getInput("test.txt").resolveFirst().expect(7)
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect(5)
        getInput("input.txt").resolveSecond()
    }

    private fun List<Int>.resolveFirst() =
        zipWithNext { first, second ->
            second > first
        }.count {
            it
        }.apply {
            println("result: $this")
        }


    private fun List<Int>.resolveSecond() =
        windowed(3).map {
            it.sum()
        }.resolveFirst()

    private fun getInput(filename: String): List<Int> {
        return FileHandling("/day1/$filename").map {
            it.toInt()
        }
    }
}
