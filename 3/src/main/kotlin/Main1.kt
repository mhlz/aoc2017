/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val input = 265149

    var currentNum = 1
    var currentBase = 1
    var coordinate = 0

    while (currentNum < input) {
        currentBase += 2
        currentNum = currentBase * currentBase
        coordinate++
    }

    val stepsToLeft = currentNum - input
    var x = coordinate - stepsToLeft
    var y = coordinate

    val result = Math.abs(x) + Math.abs(y)
    println(result)
}
