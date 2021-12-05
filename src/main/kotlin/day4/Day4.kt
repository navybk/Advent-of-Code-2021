package day4

import base.FileHandling
import base.expect
import java.lang.IllegalStateException

typealias Matrix = MutableMap<Int, MatrixData>

fun main() {
    Day4()
}

class Day4 {

    init {
        getInput("test.txt").resolveFirst().expect(4512)
        getInput("input.txt").resolveFirst()

        getInput("test.txt").resolveSecond().expect(1924)
        getInput("input.txt").resolveSecond()
    }

    private fun Pair<List<Int>, List<Matrix>>.resolveFirst(): Int {
        val max: Int = second.first().values.maxOf(MatrixData::column)

        first.forEach { number ->
            second.forEach {
                it.setAndCheck(number, max)?.let { matrixSum ->
                    return (matrixSum * number).also { result ->
                        println("result $result")
                    }
                }
            }
        }
        throw IllegalStateException("value not found")
    }

    private fun Pair<List<Int>, MutableList<Matrix>>.resolveSecond(): Int {
        val max: Int = second.first().values.maxOf(MatrixData::column)
        var lastMatrixResult: Int? = null
        first.forEach { number ->
            val iterator = second.iterator()
            var matrix = iterator.next()
            while (iterator.hasNext()) {
                matrix.setAndCheck(number, max)?.apply {
                    iterator.remove()
                    lastMatrixResult = this * number
                }
                matrix = iterator.next()
            }
        }
        return lastMatrixResult!!.apply {
            println("result $this")
        }
    }

    private fun Matrix.setAndCheck(number: Int, max: Int): Int? {
        remove(number) ?: return null
        for (index in 0..max) {
            this@setAndCheck.values
                .takeIf { unmarked ->
                    unmarked.count { it.row == index } == 0 || unmarked.count { it.column == index } == 0
                }?.apply {
                    return this@setAndCheck.keys.sum()
                }
        }
        return null
    }

    private fun getInput(filename: String): Pair<List<Int>, MutableList<Matrix>> {
        val numberMap: MutableList<Int> = mutableListOf()
        val matrixList = mutableListOf<Matrix>()
        var matrix: Matrix? = null
        var rowCount = 0

        FileHandling("/day4/$filename").iterate {
            if (it.isBlank()) {
                matrix = null
                return@iterate
            }

            if (numberMap.isEmpty()) {
                numberMap.addAll(
                    it.split(",")
                        .mapNotNull { number ->
                            number.toIntOrNull()
                        }
                )
                return@iterate
            }

            val numList = it.split(" ")
                .asSequence()
                .mapNotNull { stringValue ->
                    stringValue.toIntOrNull()
                }

            matrix = matrix ?: mutableMapOf<Int, MatrixData>().apply {
                rowCount = 0
                matrixList.add(this)
            }
            matrix!!.addRow(rowCount, numList)
            rowCount++

        }
        return numberMap to matrixList
    }

    private fun Matrix.addRow(row: Int, list: Sequence<Int>) {
        list.forEachIndexed { index, number ->
            this[number] = MatrixData(row, index)
        }
    }
}
