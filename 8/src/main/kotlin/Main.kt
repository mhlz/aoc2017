import java.io.File

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {
    val program = File("input.txt")
        .readLines()
        .filter { it.isNotBlank() }
        .map {
            val res = "(\\w+) (dec|inc) (-?\\d+) if (\\w+) (..?) (-?\\d+)"
                .toRegex()
                .matchEntire(it)

            res?.destructured ?: error("could not parse line $it")
        }
        .map { (register, operatorStr, value, conditionalRegister, condOperatorStr, condValue) ->
            Instruction(
                register = register,
                operator = when (operatorStr) {
                    "inc" -> Operator.Increment
                    "dec" -> Operator.Decrement
                    else -> error("could not parse op $operatorStr")
                },
                value = value.toInt(),
                conditionalOperator = when (condOperatorStr) {
                    "!=" -> ConditionalOperator.NotEqualTo
                    "==" -> ConditionalOperator.EqualsTo
                    ">" -> ConditionalOperator.GreaterThan
                    ">=" -> ConditionalOperator.GreaterThanOrEqualsTo
                    "<" -> ConditionalOperator.LessThan
                    "<=" -> ConditionalOperator.LessThanOrEqualsTo
                    else -> error("could not parse conditional $condOperatorStr")
                },
                conditionalRegister = conditionalRegister,
                conditionalValue = condValue.toInt()
            )
        }

    var registers = mapOf<String, Int>()
    var max = 0
    program.forEach {
        registers = it.execute(registers)
        val currentMax = registers.values.max() ?: 0
        if (currentMax > max)
            max = currentMax
    }

    val solution = registers.values.max()
    println(solution)
    println(max)
}

data class Instruction(
    val operator: Operator,
    val register: String,
    val value: Int,

    val conditionalOperator: ConditionalOperator,
    val conditionalRegister: String,
    val conditionalValue: Int
)

fun Instruction.execute(registers: Map<String, Int>): Map<String, Int> =
    if (testCondition(registers))
        doIt(registers)
    else
        registers

fun Instruction.doIt(registers: Map<String, Int>): Map<String, Int> =
    registers.getOrDefault(register, 0).let {
        when (operator) {
            Operator.Increment -> registers + (register to it + value)
            Operator.Decrement -> registers + (register to it - value)
        }
    }

fun Instruction.testCondition(registers: Map<String, Int>): Boolean =
    registers.getOrDefault(conditionalRegister, 0).let {
        when (conditionalOperator) {
            ConditionalOperator.GreaterThan -> it > conditionalValue
            ConditionalOperator.LessThan -> it < conditionalValue
            ConditionalOperator.EqualsTo -> it == conditionalValue
            ConditionalOperator.GreaterThanOrEqualsTo -> it >= conditionalValue
            ConditionalOperator.LessThanOrEqualsTo -> it <= conditionalValue
            ConditionalOperator.NotEqualTo -> it != conditionalValue
        }
    }

enum class Operator {
    Increment,
    Decrement
}

enum class ConditionalOperator {
    GreaterThan,
    LessThan,
    EqualsTo,
    GreaterThanOrEqualsTo,
    LessThanOrEqualsTo,
    NotEqualTo
}
