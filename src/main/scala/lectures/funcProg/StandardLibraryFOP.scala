package lectures.funcProg

import scala.collection.immutable.ArraySeq
import scala.util.Random

object StandardLibraryFOP extends App {
    println("==The Seq Api==")
    val aSeq = Seq(1, 3, 2, 4)
    // Seq is a trait and the companion object apply is a factory
    println(aSeq.isInstanceOf[List[Int]]) // true
    println(aSeq) // List(1, 3, 2, 4)
    println(aSeq.reverse)
    println(aSeq.sorted)
    println(aSeq(2))
    println(aSeq ++ Seq(7, 9, 8))

    println("\n\n\n==Ranges==")
    val inclusiveRange: Seq[Int] = 1 to 3
    val exclusiveRange: Seq[Int] = 1 until 3

    val printValue: Int => Unit = println(_)

    inclusiveRange.foreach(printValue)
    exclusiveRange.foreach(printValue)

    (1 to 5).foreach({println("I don't understand why this is printed only once");println(_)})

    println("\n\n==The List Api==")
    val aList = List(1, 2, 4, 3)
    // amortized O(1)
    println(s"aList.head = ${aList.head}")
    println(s"aList.tail = ${aList.tail}")
    println(s"aList.isEmpty = ${aList.isEmpty}")

    // O(n)
    println(s"aList.reverse = ${aList.reverse}")
    println(s"aList.length = ${aList.length}")
    println(s"aList(2) = ${aList(2)}")

    println(s"filter example: ${aList.filter(_ % 2 == 0)}")
    println(s"map example: ${aList.map(_ % 2 == 0)}")
    println(s"flatMap example: ${aList.flatMap(x => List(x, x + 1, 3 * x))}")

    val prepended = 42 :: aList
    val nice = 69 +: prepended :+ 420

    println(s"prepended = ${prepended}")
    println(s"69 +: prepended :+ 420 = ${nice}")
    println(s"List.fill(n)(value) = ${List.fill(3)("great success!")}")
    println(s"mkString: ${nice.mkString(" + ")}") // 69 + 42 + 1 + 2 + 4 + 3 + 420


    println("\n\n==The Array Api==")
    val numArr = Array(1,3,3,5,6)
    println(s"numArr = ${numArr.mkString(" ")}") // 1 3 3 5 6
    val threeNumbers = Array.ofDim[Int](3)

    threeNumbers.foreach(println(_))

    numArr(1) = 100 // numArr.update(1, 100)
    println(s"numArr = ${numArr.mkString(" ")}") // 1 100 3 5 6

    val seqFromArr: Seq[Int] = numArr // implicit conversion
    println(s"seqFromArr is of type ArraySeq: ${seqFromArr.isInstanceOf[ArraySeq[Int]]}")


    println("\n\n==The Vector Api==")
    val numbersVector: Vector[Int] = Vector(100, 300, 200)

    // vectors are really efficient
    def getWriteTime(collection: Seq[Int]): Double = {
        val generator = new Random
        val maxRuns = 1000
        val maxCapacity = collection.length

        val execTimes = for {
            it <- 1 to maxRuns
        } yield {
            val currentTime = System.nanoTime()
            collection.updated(generator.nextInt(maxCapacity), 0)
            System.nanoTime() - currentTime
        }

        execTimes.sum * 1.0 / maxRuns
    }

    val r = (1 to 1000000)
    val listForPerf = r.toList
    val vectorForPerf = r.toVector

    println(getWriteTime(listForPerf))
    println(getWriteTime(vectorForPerf))
}
