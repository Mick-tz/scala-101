package lectures.ooProg

/**
 * Case classes are used to define a class and its companion object which includes the following
 * defaults:
 *
 * 1. class parameters are fields.
 * 2. fields based "toString" implementation.
 * 3. field based "equals" method implementation.
 * 4. "hashcode" method implementation.
 * 5. fields based "copy" method.
 * 6. automatically creates companion objects (no need of newing up things).
 * 7. case classes are serializable.
 * 8. case classes have extractor patterns - CCs can be used in PATTERN MATCHING.
 */

abstract class AList[+A] {
    def head: A
    def tail: AList[A]
    def isEmpty: Boolean
    def add[B >:A](element: B): AList[B]
    def printElements: String

    override def toString: String = "[" + printElements + "]"

    def map[B](transformer: A => B): AList[B]
    def flatMap[B](transformer: A => AList[B]): AList[B]
    def filter(predicate: A => Boolean): AList[A]

    def ++[B >: A](list: AList[B]): AList[B]

    def forEach(f: A => Unit): Unit
    def sort(compare: (A, A) => Int): AList[A]
    def zipWith[B, C](list: AList[B], zip: (A, B) => C): AList[C]
    def fold[B](start: B)(operator: (B, A) => B): B
}

case object Empty extends AList[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: AList[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add[B >: Nothing](element: B): AList[B] = Cons(element, Empty)
    def printElements: String = ""

    def map[B](transformer: Nothing => B): AList[B] = Empty
    def flatMap[B](transformer: Nothing => AList[B]): AList[B] = Empty
    def filter(predicate: Nothing => Boolean): AList[Nothing] = Empty

    def ++[B >: Nothing](list: AList[B]): AList[B] = list

    def forEach(f: Nothing => Unit): Unit = ()
    def sort(compare: (Nothing, Nothing) => Int): AList[Nothing] = Empty

    def zipWith[B, C](list: AList[B], zip: (Nothing, B) => C): AList[C] =
        if (!list.isEmpty)
            throw new RuntimeException("List lengths don't match!")
        else
            Empty

    def fold[B](start: B)(operator: (B, Nothing) => B): B = start

}

case class Cons[+A](h: A, t: AList[A]) extends AList[A] {
    def head = h
    def tail: AList[A] = t
    def isEmpty: Boolean = false
    def add[B >: A](element: B): AList[B] = Cons(element, this)
    def printElements: String =
        if (t.isEmpty) "" + h
        else s"${h} ${t.printElements}"

    def flatMap[B](transformer: A => AList[B]): AList[B] =
        transformer(h) ++ t.flatMap(transformer)

    def map[B](transformer: A => B): AList[B] =
        Cons(transformer(h), t.map(transformer))

    def filter(predicate: A => Boolean): AList[A] =
        if (predicate(h))
            Cons(h, t.filter(predicate))
        else
            t.filter(predicate)

    def ++[B >: A](list: AList[B]): AList[B] =
        Cons(h, t ++ list)

    def forEach(f: A => Unit): Unit = {
        f(h)
        t.forEach(f)
    }

    def sort(compare: (A, A) => Int): AList[A] = {
        val sortedTail = t.sort(compare)
        if (t.isEmpty)
            this
        else if (0 < compare(h, sortedTail.head))
            Cons(h, sortedTail)
        else
            Cons(sortedTail.head, Empty) ++ sortedTail.tail.add(h).sort(compare)
    }

    def zipWith[B, C](list: AList[B], zip: (A, B) => C): AList[C] =
        if (t.isEmpty && !list.isEmpty && list.tail.isEmpty)
            Cons(zip(h, list.head), Empty)
        else if (t.isEmpty && (list.isEmpty || !list.tail.isEmpty))
            throw new RuntimeException("List lengths don't match!")
        else
            t.zipWith(list.tail, zip).add(zip(h, list.head))

    def fold[B](start: B)(operator: (B, A) => B): B =
        t.fold(operator(start, h))(operator)
}
