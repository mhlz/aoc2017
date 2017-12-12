import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = File("input.txt")
        .readLines()
        .filter { it.isNotBlank() }
        .map { "(\\d+) <-> (.+)".toRegex().matchEntire(it)!!.destructured }
        .map { (id, partners) ->
            Program(
                id = id.toInt(),
                partners = partners
                    .split(", ")
                    .map { it.toInt() }
                    .toSet()
            )
        }

    val programs = input.map { it.id to it }.toMap()

    val group0 = programs[0]!!.findGroup(programs)
    println(group0.size)

    val unchecked = programs.values.toMutableList()
    val groups = mutableSetOf<Set<Program>>()
    while (unchecked.isNotEmpty()) {
        val current = unchecked.removeAt(0)
        val currentGroup = current.findGroup(programs)
        groups.add(currentGroup)
        unchecked.removeAll(currentGroup)
    }
    println(groups.size)
}

fun Program.findGroup(programs: Map<Int, Program>): Set<Program> {
    val unvisited = mutableListOf(this)
    val visited = mutableSetOf<Program>()

    while (unvisited.isNotEmpty()) {
        val current = unvisited.removeAt(0)
        for (p in current.partners) {
            val program = programs[p]!!
            if (program in visited)
                continue
            if (program !in unvisited)
                unvisited.add(program)
        }
        visited.add(current)
    }

    return visited
}

data class Program(
    val id: Int,
    val partners: Set<Int>
)
