import java.io.File

/**
 * @author Mischa Holz
 */

fun main(args: Array<String>) {
    val input = File("input.txt").readText()

    val result = input
        .split("\n")
        .filter { it.isNotBlank() }
        .map { it.split("\t") }
        .map { it.map { it.toInt() } }
        .map { row ->
            val filteredRow = row
                .filter { x ->
                    row.any { y ->
                        x != y && (x % y == 0 || y % x == 0)
                    }
                }
            val min = filteredRow.min()!!
            val max = filteredRow.max()!!

max / min
        }.sum()


}

fun CharArray.circularAccess(n: Int): Char {
    val index = n % size

    return get(index)
}


