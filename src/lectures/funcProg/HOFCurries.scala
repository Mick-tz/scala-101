package lectures.funcProg

object HOFCurries extends App {
    def nTimes(f: Int => Int, n: Int, x: Int): Int =
        if (n <= 0) x
        else nTimes(f, n - 1, f(x))

    val byTwo: Int => Int = 2 * _

    println(nTimes(byTwo, 3, 5))

    // alternatively we can define a different HOF and used alongside lambdas and the curried notation
    def nTimesFunc(f: Int => Int, n: Int): Int => Int =
        if (n <= 0) x => x // identity
        else x => nTimesFunc(f, n - 1)(f(x)) // lambda that gets nTimesFunc(f, n - 1) and applies it to f(x)

    println(nTimesFunc(byTwo, 3)(5))

    // notice that the arrow sign is "right associative", i.e.
    // Int => Int => Int is interpreted as Int => (Int => Int), or, in other words,
    // the type for a function that takes in an integer and returns a function
    // with domain Int and counter domain Int
    val addFuncFactory: Int => Int => Int = (x: Int) => _ + x
    val multiplyFuncFactory: Int => (Int => Int) = (x: Int) => _ * x

    val addSeven = addFuncFactory(7)
    println(addSeven(8))
    println(multiplyFuncFactory(3)(2)) // using curried syntax

    // Thus, we have to use parentheses when the parameter is actually a function
    val getFunctionHash: (Int => Int) => Int = _.hashCode
    val selfCompose: (Int => Int) => (Int => Int) = (f: Int => Int) => x => f(f(x))
    println(s"multiplyFuncFactory(4) hash: ${getFunctionHash(multiplyFuncFactory(4))}")
    println(s"new multiplyFuncFactory(4) hash: ${getFunctionHash(multiplyFuncFactory(4))}")
    println(s"multiplyFuncFactory(3) hash: ${getFunctionHash(multiplyFuncFactory(3))}")
    println(s"self composed hash: ${getFunctionHash(selfCompose(multiplyFuncFactory(3)))}")

    // this can get really complex, e.g.
    // Int => Int => Int => Int will be translated to Int => (Int => Int => Int)
    // (Int => Int => Int) will be translated to Int => (Int => Int)

    // An int m, is sent to the function that takes an int b and returns a function
    // that takes and int x and calculates m * x + b
    val straightLineFactory: Int => Int => Int => Int =
        m => b => x => m * x + b

    val lineFunc = straightLineFactory(2)(1)
    println(s"line's hash: ${getFunctionHash(lineFunc)}")
    println(lineFunc(0)) // (0, 1) \in Line
    println(lineFunc(1)) // (1, 3) \in Line

    // now, this is cumbersome and hard to reason about, to address this
    // scala actually allows you to have multiple parameter lists at declaration
    def logFormatter(separator: String)(logs: Array[String]): String = List.from(logs).mkString(separator)

    val commaLogs = logFormatter(",")
    val barLogs = logFormatter("|")
    val theLogs: Array[String] = Array.from(Seq("Error1", "Error2"))


    println(commaLogs(theLogs))
    println(barLogs(theLogs))

}
