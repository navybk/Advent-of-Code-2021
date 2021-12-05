package day2

import base.FileHandling
import base.expect

fun main() {
    Day2()
}

class Day2 {

    init {
        getInput("test.txt").resolveFirst().expect(150)
        getInput("input.txt").resolveFirst()

        getInput("test.txt").resolveSecond()
        getInput("input.txt").resolveSecond()
    }

    private fun List<Pair<Direction, Int>>.resolveFirst() =
        groupBy(
            { it.first }, { it.second }
        ).map {
            it.key to it.value.sum()
        }.toMap()
            .run {
                val forward = getValue(Direction.FORWARD)
                val depth = getValue(Direction.DOWN) - getValue(Direction.UP)
                forward * depth
            }.also {
                println("result: $it")
            }

    private fun List<Pair<Direction, Int>>.resolveSecond(): Int {
        var aim = 0
        var horizontal = 0
        var depth = 0
        forEach {
            when (it.first) {
                Direction.FORWARD -> {
                    horizontal += it.second
                    depth += aim * it.second
                }
                Direction.DOWN -> aim += it.second
                Direction.UP -> aim -= it.second
            }
        }
        return (depth * horizontal).also {
            println("result: $it")
        }
    }

    private fun getInput(filename: String): List<Pair<Direction, Int>> {
        return FileHandling("/day2/$filename").map { line ->
            line.split(" ").let {
                Direction.fromString(it[0]) to it[1].toInt()
            }
        }
    }
}
