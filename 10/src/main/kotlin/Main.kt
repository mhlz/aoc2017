import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val list = (0..255).toList().toIntArray()
    var currentPosition = 0
    var skipSize = 0
    val input = File("input.txt")
        .readText()
        .trim()
        .map { it.toInt() }

    val lengths = input + listOf(17, 31, 73, 47, 23)

    for (round in 1..64) {
        lengths.forEach {
            list.reverse(currentPosition, currentPosition + it - 1)
            currentPosition += it + skipSize
            skipSize++
        }
    }

    val sparseHash = list
    val denseHash = (0..15)
        .map { sparseHash.slice(it * 16..it * 16 + 15) }
        .map { blockValues -> blockValues.reduce { acc, it -> acc xor it } }

    val denseHashStr = denseHash
        .joinToString("") { it.toString(16).padStart(2, '0') }
    println(denseHashStr)
}

fun IntArray.read(n: Int): Int = get(n % size)
fun IntArray.write(n: Int, value: Int) = set(n % size, value)
fun IntArray.reverse(start: Int, end: Int): IntArray {
    val half = start + ((end + 1) - start) / 2
    var j = end
    for (i in start until half) {
        swap(i, j)
        j--
    }
    return this
}

fun IntArray.swap(one: Int, two: Int): IntArray {
    val tmp = read(one)
    write(one, read(two))
    write(two, tmp)
    return this
}
