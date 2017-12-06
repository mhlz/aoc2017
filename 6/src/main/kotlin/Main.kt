import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = File("input.txt").readText().trim()

    val banks = input
        .split("\\s".toRegex())
        .map { it.toInt() }
        .toIntArray()

    val knownConfigs = mutableSetOf(banks.config())

    var cycles = 0
    while (true) {
        banks.cycle()
        cycles++
        if (banks.config() in knownConfigs)
            break
        println(banks.config())
        knownConfigs += banks.config()
    }
    println("task 1: " + cycles)

    cycles = 0
    val startState = banks.config()
    while (true) {
        banks.cycle()
        cycles++
        if (banks.config() == startState)
            break
        println(banks.config())
    }

    println("task 2: " + cycles)
}

fun IntArray.config() = map { it.toString() }.joinToString("|") { it.padStart(3, '0') }

fun IntArray.cycle() {
    var maxPos = 0
    var max = 0
    for (i in indices) {
        if (this[i] > max) {
            max = this[i]
            maxPos = i
        }
    }

    var toRedistribute = this[maxPos]
    this[maxPos] = 0
    val packs = Math.ceil(toRedistribute.toDouble() / this.size.toDouble()).toInt()
    var bucket = (maxPos + 1) % this.size
    while (toRedistribute >= packs) {
        this[bucket] += packs
        toRedistribute -= packs
        bucket = (bucket + 1) % this.size
    }
    this[bucket] += toRedistribute
}
