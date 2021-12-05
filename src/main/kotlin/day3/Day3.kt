package day3

import base.FileHandling
import base.expect

fun main() {
    Day3()
}

class Day3 {

    init {
        getInput("test.txt").resolveFirst().expect(198)
        getInput("input.txt").resolveFirst()

        getInput("test.txt").resolveSecond().expect(230)
        getInput("input.txt").resolveSecond()
    }

    private fun FileHandling.resolveFirst(): Int {
        var count = 0
        lateinit var sumArray: Array<Int>
        iterate {
            if (count == 0) {
                sumArray = Array(it.length) { 0 }
            }
            count++

            it.forEachIndexed { index, character ->
                sumArray[index] = sumArray.getOrElse(index) {
                    0
                } + character.toString().toInt()
            }
        }
        val half = count / 2

        var gamma = ""
        var epsilon = ""
        sumArray.forEach {
            if (it > half) {
                gamma += "1"
                epsilon += "0"
            } else {
                gamma += "0"
                epsilon += "1"
            }
        }

        return (gamma.asBinaryInt() * epsilon.asBinaryInt()).also {
            println("result: $it")
        }
    }

    private fun FileHandling.resolveSecond(): Int {
        var index = 1
        var (oxy, co2) = getStringList().split(0)
            .let {
                if (it.first.count() >= it.second.count()) {
                    it.first to it.second
                } else {
                    it.second to it.first
                }
            }

        while (oxy.count() > 1 || co2.count() > 1) {
            if (oxy.count() > 1) {
                oxy = oxy.split(index).getMajor()
            }

            if (co2.count() > 1) {
                co2 = co2.split(index).getMajor(true)
            }
            index++
        }

        return (oxy[0].asBinaryInt() * co2[0].asBinaryInt()).also {
            println("result: $it")
        }
    }

    private fun <T> Pair<List<T>, List<T>>.getMajor(inverted: Boolean = false): List<T> =
        if (inverted.xor(first.count() >= second.count())) {
            first
        } else {
            second
        }

    private fun List<String>.split(index: Int) =
        partition {
            it[index] == "1".single()
        }

    private fun String.asBinaryInt() = Integer.parseInt(this, 2)

    private fun getInput(filename: String): FileHandling {
        return FileHandling("/day3/$filename")
    }
}