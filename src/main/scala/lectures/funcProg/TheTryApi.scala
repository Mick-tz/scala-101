package lectures.funcProg

import scala.util.{Failure, Random, Success, Try}

object TheTryApi extends App {

    println("== The Try class==")
    // the Try class is extended by 2 case classes
    val aSuccess = Success(3)
    val aFailure = Failure(new RuntimeException("example exception"))

    def unsafeMethod(): String = throw new NotImplementedError()

    // similarly to options, we don't use the case classes directly
    val possibleFailure = Try(unsafeMethod())
    println(possibleFailure) // Failure(scala.NotImplementedError: an implementation is missing)

    // Try can be used with "anonymous expressions"
    val willThisFail = Try {
        val rand = new Random(System.nanoTime())
        if (rand.nextBoolean()) throw new RuntimeException("something failed")
        else "Successful operation"
    }

    println(if (willThisFail.isSuccess) willThisFail else "There was a failure")

    // Try also have a orElse method
    println(willThisFail.orElse(Try("what goes in the orElse must be a success")))

    println("==Try has all the normal methods==")

}
