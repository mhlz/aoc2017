import java.io.File

/**
 * @author Mischa Holz
 */

fun main(args: Array<String>) {
    val input = File("input.txt").readText().trim().toCharArray()
    println("hi")

    val sum = input
        .filterIndexed { index, c ->
            input.circularAccess(index + input.size / 2) == c
        }
        .map { it.toString().toInt() }
        .sum()
    println(sum)
}

fun CharArray.circularAccess(n: Int): Char {
    val index = n % size

    return get(index)
}

