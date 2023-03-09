package lectures.funcProg

import scala.annotation.tailrec

object NaiveSocialNetwork extends App {
    def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
        network + (person -> Set())

    def friend(network: Map[String, Set[String]], person: String, newFriend: String): Map[String, Set[String]] = network +
            (person -> (network(person) + newFriend)) +
            (newFriend -> (network(newFriend) + person))


    def unfriend(network: Map[String, Set[String]], person: String, friend: String): Map[String, Set[String]] =
        network +
            (person -> (network(person) - friend)) +
            (friend -> (network(friend) - person))

    def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
        network
            .filter(connection => !connection._1.equals(person))
            .map(connection => connection._1 -> (connection._2 - person))

    def numberOfFriends(network: Map[String, Set[String]], person: String): Int =
        network(person).size

    def mostPopular(network: Map[String, Set[String]]): String =
        network.maxBy(connection => connection._2.size)._1

    def loners(network: Map[String, Set[String]]): Map[String, Set[String]] =
        network.filter(_._2.isEmpty)

    def numberOfLoners(network: Map[String, Set[String]]): Int =
        network.count(_._2.isEmpty)

    def areReachable(network: Map[String, Set[String]], personA: String, personB: String): Boolean = {
        @tailrec
        def bfs(person: String, considered: Set[String], possible: Set[String]): Boolean = {
            if (possible.isEmpty) false
            else
                if (possible.head.equals(person)) true
                else if (considered.contains(possible.head))
                    bfs(person, considered, possible.tail)
                else bfs(person, considered + possible.head, possible.tail ++ network(possible.head))
        }

        bfs(personB, Set(), network(personA))
    }

    val network = add(add(add(add(add(add(Map(), "chino"),"laura"), "juan"), "miguel"), "claudia"), "loner")

    val connectedNetwork = friend(friend(friend(friend(friend(network,
        "chino", "laura"),
        "juan", "claudia"),
        "claudia", "miguel"),
        "laura", "miguel"),
        "claudia", "laura")

    println(unfriend(connectedNetwork, "juan", "claudia"))
    println(remove(connectedNetwork, "claudia"))
    println(numberOfFriends(connectedNetwork, "claudia"))
    println(mostPopular(connectedNetwork))
    println(loners(connectedNetwork))
    println(numberOfLoners(connectedNetwork))
    println("can chino reach claudia?")
    println(areReachable(connectedNetwork, "chino", "claudia"))
    println("can chino reach loner?")
    println(areReachable(connectedNetwork, "chino","loner"))

}
