import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = File("input.txt")
        .readLines()
        .map {
            "(.*) \\((\\d+)\\)( -> (.*))?".toRegex().matchEntire(it) ?: error(it)
        }
        .map {
            val name = it.groupValues[1]
            val weight = it.groupValues[2].toInt()

            val childrenStr = it.groupValues[4].split(", ").filter { it.isNotEmpty() }

            Triple(name, weight, childrenStr)
        }
        .toMutableList()

    val builtPrograms = mutableMapOf<String, Program>()
    while (input.isNotEmpty()) {
        val iterator = input.iterator()
        while (iterator.hasNext()) {
            val (name, weight, childrenNames) = iterator.next()
            if (!childrenNames.all { it in builtPrograms })
                continue

            val program = Program(
                name = name,
                weight = weight,

                children = childrenNames.map { builtPrograms[it] ?: error(it) }
            )

            builtPrograms[name] = program

            iterator.remove()
        }
    }

    val unbalanced = builtPrograms.filter { (_, it) -> !it.isBalanced() }
    println()
}

data class Program(
    val name: String,
    var weight: Int,

    val children: List<Program>
)

fun Program.calculateWeight(): Int = weight + children.map { it.calculateWeight() }.sum()

fun Program.isBalanced(): Boolean = when {
    children.map { it.calculateWeight() }.toSet().size <= 1 -> true
    else -> false
}
