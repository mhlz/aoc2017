import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = File("input.txt")
        .readLines()
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toMutableList()

    var pos = 0
    var steps = 0
    while (pos >= 0 && pos < input.size) {
        val currentInstruction = input[pos]
        val oldPos = pos
        pos += currentInstruction

        if (currentInstruction >= 3)
            input[oldPos]--
        else
            input[oldPos]++

        steps++
    }
    println(steps)
}
