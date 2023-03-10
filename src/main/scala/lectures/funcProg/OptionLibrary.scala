package lectures.funcProg

import scala.util.Random

object OptionLibrary extends App {
    println("We use options to deal with unsafe apis")
    // Option has 2 case classes, Some and None

    def unsafeMethod(): String = null
    // we shouldn't use the case classes directly
    val wrongUsage = Some(unsafeMethod()) // this defies the purpose of the Option class

    // naive example on how things could break
    def reverseString:String => String = _.reverse
    try {
        println(reverseString(wrongUsage.value))
    } catch {
        case e:Throwable => println(s"Exception has been thrown: $e") // NullPointerException
    }
    println(wrongUsage) // Some(null)


    println("Use the Option class directly instead")
    val correctUsage = Option(unsafeMethod()) // this will yield None
    println(reverseString(correctUsage.getOrElse("eulav oN")))

    println("The best approach is to chain Option.orElse whenever we use an unsafe method")
    val bestUsage: String = Option(unsafeMethod()).getOrElse("tneserp saw eulav oN")
    println(reverseString(bestUsage)) // No value was present

    println("Other commonly used approaches imply creating methods that take \"optional parameters\"")
    def backupMethod(): String = "Backup"
    val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
    println(chainedResult) // Some

    def unsafeWithOption(): Option[String] = None
    def backupWithOption(): Option[String] = Some("No value found")
    val chainedOptionalDesignedApis = unsafeWithOption().orElse(backupWithOption())

    println("== the Option Api ==")

    val hasValue: Option[Int] = Some(4)
    val noValue: Option[Int] = None

    println(hasValue.isEmpty)
    try {
        println(noValue.get) // unsafe, do not use
    } catch {
        case e => println(s"Exception: $e") // NoSuchElementException
    }

    println(hasValue.map(_ * 2)) // Some(8)
    println(hasValue.filter(_ % 2 == 1)) // None
    println(hasValue.flatMap(x => Option(x * 10))) // Some(40)


    println("Using for comprehensions in Option")
    class Connection {
        def connect = "connected"
    }
    object Connection {
        val random = new Random(System.nanoTime())

        def apply(host: String, port: String): Option[Connection] = {
            if (random.nextBoolean()) Some(new Connection)
            else None
        }
    }

    val config: Map[String, String] = Map(
        "host" -> "127.0.0.1",
        "port" -> "8080"
    )
    // the map get method returns optional values
    val host = config.get("host")
    val port = config.get("port")

    // instead of using Connection(host.get, port.get) which would be unsafe or using if-else statements
    // we would use the FOP approach
    val connection = host.flatMap(h => port.flatMap(p => Connection(h,p)))
    val connectionStatus = connection.map(c => c.connect)
    println("possible result: ")
    connectionStatus.foreach(println) // will only print if there was a connection

    // alternatively, the for comprehension syntax is quite clear
    val status = for {
        host <- config.get("host")
        port <- config.get("port")
        connection <- Connection(host, port)
    } yield connection.connect
    println("possible result after doing for comprehension: ")
    status.foreach(println)
}
