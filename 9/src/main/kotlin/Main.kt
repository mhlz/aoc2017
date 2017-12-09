import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = File("input.txt")
        .readText().trim()

    val init = ParserState(
        state = State.Group,
        escaped = false,
        scores = listOf(0),
        level = 0,
        garbageCounter = 0
    )

    var state = init
    input.forEach {
        state = state.parse(it)
    }
    println(state.scores.single())

    println(state.garbageCounter)
}

enum class State {
    Group,
    Garbage
}

data class ParserState(
    val state: State,
    val escaped: Boolean,

    val scores: List<Int>,
    val level: Int,

    val garbageCounter: Int
)

fun ParserState.parse(char: Char): ParserState = when (state) {
    State.Garbage -> parseGarbage(char)
    State.Group -> parseGroup(char)
}

fun ParserState.parseGarbage(char: Char): ParserState = when {
    escaped -> copy(escaped = false)
    else -> when (char) {
        '!' -> copy(escaped = true)
        '>' -> copy(state = State.Group)
        else -> copy(garbageCounter = garbageCounter + 1)
    }
}

fun ParserState.parseGroup(char: Char): ParserState = when (char) {
    '{' -> copy(
        scores = scores + (level + 1),
        level = level + 1
    )
    '<' -> copy(state = State.Garbage)
    '}' -> copy(
        scores = scores.dropLast(2) + (
            scores.last() + scores.dropLast(1).last()
            ),
        level = level - 1
    )
    ',' -> copy()
    else -> error("invalid character $char")
}
