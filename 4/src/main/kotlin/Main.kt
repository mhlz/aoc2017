import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = File("input.txt").readLines()

    val result = input
        .filter {
            val words = it
                .split("\\s".toRegex())
            val uniqueCount = words
                // task 2
                // .map { it.chars().sorted().toArray().toList() }
                .toSet()
                .size

            uniqueCount == words.size
        }
        .size
    println(result)
}
