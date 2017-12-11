import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = File("input.txt")
        .readText()
        .trim()
        .split(",")
        .map {
            when (it) {
                "n" -> Direction.North
                "ne" -> Direction.NorthEast
                "se" -> Direction.SouthEast
                "s" -> Direction.South
                "sw" -> Direction.SouthWest
                "nw" -> Direction.NorthWest
                else -> error("invalid direction $it")
            }
        }

    var max = 0
    val finalCoord = input
        .map { directions[it]!! }
        .reduce { acc, coord ->
            val ret = acc + coord
            val distance = Coord(0, 0).distance(ret)
            if (distance > max)
                max = distance
            ret
        }

    val distance = Coord(0, 0).distance(finalCoord)
    println(distance)
    println(max)
}

data class Coord(
    val q: Int,
    val r: Int,
    val s: Int = -q - r
) {
    operator fun plus(other: Coord): Coord {
        return Coord(q + other.q, r + other.r)
    }

    fun distance(other: Coord): Int {
        return (Math.abs(q - other.q)
            + Math.abs(q + r - other.q - other.r)
            + Math.abs(r - other.r)) / 2
    }
}

enum class Direction {
    North,
    NorthEast,
    SouthEast,
    South,
    SouthWest,
    NorthWest
}

val directions = mapOf(
    Direction.North to Coord(0, -1),
    Direction.NorthEast to Coord(1, -1),
    Direction.SouthEast to Coord(1, 0),
    Direction.South to Coord(0, 1),
    Direction.SouthWest to Coord(-1, 1),
    Direction.NorthWest to Coord(-1, 0)
)
