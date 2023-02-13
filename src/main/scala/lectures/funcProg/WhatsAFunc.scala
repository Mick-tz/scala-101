package lectures.funcProg

object WhatsAFunc extends App {

    val doubler = new AFunc[Int, Int] {
        override def apply(element:  Int): Int = 2 * element
    }
    println(doubler(2))

    // function types are similar to what we've done
    val stringToIntConverter = new Function1[String, Int] {
        override def apply(v1: String): Int = v1.toInt
    }
    println(stringToIntConverter("1") + 4)

    // naturally, function types can be used for type checking
    val compose = {
        new Function2[Function1[String, String], Function1[String, String], Function1[String, String]] {
            override def apply(f: Function1[String, String], g: Function1[String, String]): Function1[String, String] =
                new Function1[String, String] {
                    override def apply(v1: String): String = f(g(v1))
                }
        }
    }

    val trim: Function1[String, String] = new Function1[String, String] {
        override def apply(v1: String): String = v1.trim
    }

    val reverse: Function1[String, String] = new Function1[String, String] {
        override def apply(v1: String): String = v1.reverse
    }

    val trimAndReverse = compose(trim, reverse)

    println(trimAndReverse("  hola  nene  "))

    // now that was lame, the better way to do it is using the better syntax
    val japanize: String => String = new Function1[String, String] {
        override def apply(v1: String): String = "I think I'm turning japanese: ".concat(v1)
    }

    println(japanize("Follow me"))

    // Functions that take other functions as parameters
    // or that return a function are called higher order functions.
    // In the latter case, we could also call them "Curried" functions
    // as we can chain the parameters we pass to them to obtain the final result, e.g.
    println(compose(japanize, trimAndReverse)("     !maeb gnizinapaj "))
}

// a trait that only requires apply to be implemented
trait AFunc[A, B] {
    def apply(element: A): B
}