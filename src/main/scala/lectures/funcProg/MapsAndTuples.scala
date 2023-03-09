package lectures.funcProg

object MapsAndTuples extends App {
    println("\n\n==Tuples and Maps==")
    //    val aTuple = new Tuple2(2, "(Int, String) tuple") // Tuple2[Int, String]
    // alternatively
    //    val anotherTuple = Tuple2(3, "another tuple") // companion object apply method
    val ssTuple = (4, "Syntactic Sugar")
    // tuples can group at most 22 elements of different types
    println(s"tuple._1 = ${ssTuple._1}")
    println(s"tuple.swap = ${ssTuple.swap}")
    println(s"tuple.copy(_2 = \"redefine a value\")= ${ssTuple.copy(_2 = "new value")}")

//    val emptyMap: Map[String, Int] = Map()
    val ages: Map[String, Int] = Map(("chino", 31), "laura" -> 35) // both (a, b) and a -> b work

    println(ages.contains("juan"))
    println(ages("chino"))
    try {
        println(ages("juan"))
    } catch {
        case e: Throwable => println(s"this would have been an exception: ${e}")
    }

    // alternatively to try catch we can set a default value
    val phonebook = Map("chino" -> 666).withDefaultValue(-1)
    println(phonebook("juan")) // -1

    val newPhonebook = phonebook + ("JUAN" -> 69) + ("juan" -> 420)
    println(newPhonebook("juan"))

    println(newPhonebook.mapValues(phoneNumber => 951000 + phoneNumber)) // depracated
    // be careful when mapping, e.g. here one of the juan's gets lost
    println(s"original map: $newPhonebook")
    println(s"new map: ${newPhonebook.map(pair => pair._1.toUpperCase -> pair._2)}") //

    println(newPhonebook.filterKeys(_.startsWith("j"))) // depracated
    println(newPhonebook.filter(pair => pair._1.startsWith("j"))) // correct

    println(newPhonebook.toList) // returns a List of tuples
    println(List(("chino", 1)).toMap) // List of tuples can be casted into a map

    val names = List("chino", "laura", "juan", "jose", "julian", "coco")
    println(names.groupBy(_.charAt(0))) // j -> List(juan, jose, julian), l -> List(laura), c -> List(chino, coco)

    println(newPhonebook.maxBy(pair => pair._2))
    println(s"numbers in ${newPhonebook.values} above 100: ${newPhonebook.count(pair => 100 < pair._2)}")
}
