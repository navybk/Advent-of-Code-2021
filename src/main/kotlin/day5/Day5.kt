package day5

import base.FileHandling
import base.expect
import kotlin.math.abs

typealias Matrix = MutableMap<Pair<Int, Int>, Int>

fun main() {
    Day5()
}

class Day5 {

    init {
        getIntput("test.txt").resolveFirst().expect(5)
        getIntput("input.txt").resolveFirst()

        getIntput("test.txt").resolveSecond().expect(12)
        getIntput("input.txt").resolveSecond()
    }

    private fun List<Vector>.resolveFirst(): Int {
        val matrix: Matrix = mutableMapOf()

        filter {
            it.x1 == it.x2 || it.y1 == it.y2
        }.setStraightVectors(matrix)

        return matrix.getMultiPointCount()
    }

    private fun List<Vector>.resolveSecond(): Int {
        val matrix: Matrix = mutableMapOf()

        val (straight, rest) = partition {
            it.x1 == it.x2 || it.y1 == it.y2
        }
        straight.setStraightVectors(matrix)

        rest.filter {
            abs(it.x1 - it.x2) == abs(it.y1 - it.y2)
        }.setDiagonalVectors(matrix)

        return matrix.getMultiPointCount()
    }

    private fun Matrix.getMultiPointCount() =
        values.count {
            it >= 2
        }.apply {
            println("result: $this")
        }

    private fun List<Vector>.setDiagonalVectors(matrix: Matrix) {
        forEach {
            for ((indexX, indexY) in parallelSequence(it)) {
                matrix.merge(indexX to indexY, 1, Int::plus)
            }
        }
    }

    private fun List<Vector>.setStraightVectors(matrix: Matrix) {
        forEach {
            for (indexX in ranged(it.x1, it.x2)) {
                for (indexY in ranged(it.y1, it.y2)) {
                    matrix.merge(indexX to indexY, 1, Int::plus)
                }
            }
        }
    }

    private fun ranged(first: Int, second: Int) =
        if (first < second) {
            first..second
        } else {
            first downTo second
        }

    private fun getIntput(filename: String): List<Vector> =
        FileHandling("/day5/$filename").map {
            val (x1, y1, x2, y2) = it.split(",", " -> ")
                .map(String::toInt)

            Vector(x1, y1, x2, y2)
        }

    private fun parallelSequence(vector: Vector) =
        sequence {
            yieldAll(
                ranged(vector.x1, vector.x2)
            )
        }.zip(sequence {
            yieldAll(
                ranged(vector.y1, vector.y2)
            )
        })
}