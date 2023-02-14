package lectures.ooProg

object oopTests extends App {
    var list: AList[Int] = Cons(1, Empty)
    list = Cons(7, list)
    list = Cons(3, list)
    list = Cons(5, list)
    list = Cons(5, list)
    list = list ++ list

    val theList = Cons(10, list.asInstanceOf[Cons[Int]].copy())

    // 10, 5, 3, 7, 1
    println(theList)


    val usualSort = (x: Int, y: Int) => y - x
    val reverseSort: (Int, Int) => Int = _ - _

    val sortedList = theList.sort(usualSort)
    val reversedList = theList.sort(reverseSort)
    println(sortedList)
    println(reversedList)

    val sum: (Int, Int) => Int = _ + _
    println(s"The doubled values of the list are: ${theList.zipWith(theList, sum)}")
    println(Empty.zipWith(Empty, sum))
    println(s"$reversedList + $sortedList: ${reversedList.zipWith(sortedList, sum)}")
    println(s"$sortedList + $reversedList: ${sortedList.zipWith(reversedList, sum)}")
    println(s"$theList + $sortedList: ${theList.zipWith(sortedList, sum)}")

    try {
        println(theList.zipWith(list, sum));
    } catch {
        case e: RuntimeException => println("Can't zip with a list with less elements")
    }

    try {
        println(list.zipWith(theList, sum));
    } catch {
        case e: RuntimeException => println("Can't zip with a list with more elements")
    }

    try {
        println(theList.zipWith(Empty, sum));
    } catch {
        case e: RuntimeException => println("Can't zip a non empty list with an empty list")
    }

    try {
        println(Empty.zipWith(theList, sum));
    } catch {
        case e: RuntimeException => println("Can't zip an Empty list with a non empty list")
    }

    val asSum = (x: Int, y: Int) => s"+ ${x} "
    val asProd = (x: Int, y: Int) => s"* ${x} "
    val start = 2

    println(
        s"${start} ${theList.zipWith(theList, asSum)} = ${theList.fold(start)(sum)}"
            .replace("[", "")
            .replace("]", "")
    )
    println(
        s"${start} ${theList.zipWith(theList, asProd)} = ${theList.fold(start)(_ * _)}"
            .replace("[", "")
            .replace("]", "")
    )
}
