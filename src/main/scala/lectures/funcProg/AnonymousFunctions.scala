package lectures.funcProg

object AnonymousFunctions extends App {

    // the actual way we typically implement functions is through anonymous functions, aka lambdas.
    val doubler = (x: Int) => 2 * x

    // alternatively, if we type the variable,
    val quad: Int => Int = x => x * x

    // typing multi parameter functions requires to include parentheses
    val add: (Int, Int) => Int = (a, b) => a + b

    // when we dont have parameters we just use empty parentheses
    val getHello: () => String = () => "hullo!"

    // notice functions without parameters must be called passing the empty parenthesis

    println(getHello) // prints object's pointer
    println(getHello()) // actually print returned string

    // in contrast to object methods
    val chino = "   chino   "
    println(chino.trim()) // prints the trimmed value
    println(chino.trim) // also prints the trimmed value

    // we can create lambdas using curly braces
    val stringToIntPlusOne = {
        (str: String) =>
            var a = str.toInt
            // do something else
            println(s"The original value was ${a}")
            a + 1
    }

    println(stringToIntPlusOne("2"))

    // Additionally, when parameters are only used once, we can use "_" instead
    // as long as there is no conflict inferring what parameter is being used
    val divide: (Float, Float) => Float = _ / _ // this works and will infer both params are used in order
    println(divide(1, 5))

    val divideAndThenAdd: (Float, Float) => Float = (a, b) => a / b + a
    // This will not work -> val divideAndThenAdd: (Float, Float) => Float = _ / _ + _
    // since it's not clear how parameters are used
    println(divideAndThenAdd(1, 5))

    val triplicate: Int => Int = a => a + a + a
    // this will also not work
    // -/-> val triplicate: Int => Int = _ + _ + _
    println(triplicate(3))

    val dupAndAddOne: Int => Int = a => a + a + 1
    // this will also not work
    // -/-> val dupAndAddOne: Int => Int = _ + _ + 1
    println(dupAndAddOne(3))

    // in other words, the number of "_"'s must correspond to the number of parameters
    // and we should always type annotate this kind of declarations.

}
