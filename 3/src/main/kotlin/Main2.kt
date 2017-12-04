/**
 * @author Mischa Holz
 */

fun main(args: Array<String>) {
    val input = 265149

    val results = mutableMapOf<Pair<Int, Int>, Int>()

    data class Context(val x: Int, val y: Int)

    fun Context.left() = Context(x - 1, y)
    fun Context.down() = Context(x, y - 1)
    fun Context.up() = Context(x, y + 1)
    fun Context.right() = Context(x + 1, y)
    fun Context.access() = results[x to y]

    fun Context.calculate(): Int = listOf(
        left(),
        left().down(),
        down(),
        right().down(),
        right(),
        right().up(),
        up(),
        left().up()
    ).mapNotNull {
        it.access()
    }.sum()

    fun calculate(x: Int, y: Int) {
        results[x to y] = Context(x, y).calculate()

        if (results[x to y]!! > input)
            throw RuntimeException("result: ${results[x to y]}")
    }

    var x = 0
    var y = 0
    results.put(x to y, 1)
    while (true) {
        val startX = x
        val startY = y

        x++
        calculate(x, y)

        // up from the start
        while (y < (-startY + 1)) {
            y++
            calculate(x, y)
        }

        // left
        while (x > (-startX - 1)) {
            x--
            calculate(x, y)
        }

        // down
        while (y > (startY - 1)) {
            y--
            calculate(x, y)
        }

        // right
        while (x < (startX + 1)) {
            x++
            calculate(x, y)
        }
    }
}

