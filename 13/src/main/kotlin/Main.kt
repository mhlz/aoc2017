import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = File("input.txt")
        .readLines()
        .filter { it.isNotBlank() }
        .map { it.split(": ") }
        .map { (l, d) -> l.toInt() to d.toInt() }

    var delay = 0
    while (true) {
        val state = State(
            depths = Array(100) { 1 },
            actors = mutableListOf()
        )
        input.forEach { (layer, depth) ->
            state.depths[layer] = depth
            val scanner = Scanner(state, layer = layer, depth = 0)
            state.actors.add(scanner)
        }

        val packet = Packet(
            state
        )
        state.actors.add(packet)

        var waited = 0
        while (packet.layer <= 98) {
            if (delay <= waited)
                state.actors.filterIsInstance<Packet>().forEach {
                    it.act()
                }

            state.actors.filterIsInstance<Scanner>().forEach {
                it.act()
            }
            waited++
        }
        println(packet.severity)
        if (packet.severity == 0)
            break
        delay++
    }

    println(delay)
}

abstract class Actor(
    val state: State,
    var layer: Int,
    var depth: Int
) {
    fun moveTo(layer: Int, depth: Int) {
        this.layer = layer
        this.depth = depth
    }

    abstract fun act()
}

class Scanner(
    state: State,
    var direction: Direction = Direction.Down,
    layer: Int,
    depth: Int
) : Actor(state, layer, depth) {
    override fun act() {
        val maxDepth = state.depths[layer]
        val newDepth = depth + direction
        if (newDepth < 0 || newDepth >= maxDepth) {
            direction = direction.reverse
        }

        moveTo(layer, depth + direction)
    }
}

class Packet(
    state: State,
    var severity: Int = 0
) : Actor(state, -1, 0) {
    override fun act() {
        moveTo(layer + 1, depth)
        val other = state.actors.find { it.layer == layer && it.depth == depth }
        if (other != null && other != this)
            severity += layer * state.depths[layer]
    }
}

enum class Direction {
    Up,
    Down
}

operator fun Int.plus(direction: Direction) = when (direction) {
    Direction.Up -> this - 1
    Direction.Down -> this + 1
}

val Direction.reverse
    get() = when (this) {
        Direction.Down -> Direction.Up
        Direction.Up -> Direction.Down
    }

data class State(
    val depths: Array<Int>,
    val actors: MutableList<Actor>
)
