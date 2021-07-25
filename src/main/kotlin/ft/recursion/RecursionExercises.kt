package ft.recursion


// Taken from http://tmorris.net/posts/scala-exercises-for-beginners/index.html

/**
 * Ok here are the rules.
 *
 * You can't use any of the standard list functions, like `map`, `filter`, `flatMap`, `append`, `:::`, `:+`, etc.
 *
 * But you can always use `::` to construct a new list by prepending an element to another list.
 *
 * You CAN and are encouraged to use the solutions from the exercises below to solve the harder
 * ones towards the end.
 *
 * Keep an eye out for repetition and similarities between your answers.
 *
 * REMEMBER: Follow the types, they almost always guide you to the solution.  If it compiles and looks a little
 * too simple, it's probably correct.  As Sherlock Holmes once said, "Each one is suggestive, together they are
 * most certainly conclusive."
 *
 * See if you can make your solution tail recursive, where possible.
 *
 */

sealed class FunList<out A> {
  companion object {
    operator fun <A>invoke(vararg l:A ): FunList<A> = invoke(listOf(*l))
    operator fun <A>invoke(l:List<A>): FunList<A> = l.foldRight(Nil, { e:A, acc: FunList<A> -> Cons<A>(e,acc)})
  }
}


object Nil : FunList<Nothing>()
data class Cons<out A>(val head: A, val tail: FunList<A>): FunList<A>()

object RecursionExercises {

  fun plusOne(n: Int) = n + 1

  fun minusOne(n: Int) = n - 1

  // Add two non-negative Integers together.  You are only allowed to use plusOne and minusOne above
  tailrec fun add(a: Int, b: Int): Int =
    if (b == 0)
      a
    else
      add(plusOne(a), minusOne(b))

  // You are not permitted to use any list functions such as map, flatMap, ++, flatten etc
   fun sum(l: FunList<Int>): Int = when(l) {
     is Cons -> {
       val (head, tail) = l
       head + sum(tail)
     }
     else -> 0
   }

  //Again no list functions are permitted for the following
  fun <A> length(x: FunList<A>): Int = when(x) {
    is Cons -> {
      val (head, tail) = x
      1 + length(tail)
    }
    else -> 0
  }

  // Do you notice anything similar between sum and length? Hmm...

  // Mapping over a list.  You are given a FunList of type A and a function converting an A to a B
  // and you give back a list of type B.  No list functions allowed!
  fun <A, B> map(x: FunList<A>, f: (A) -> B): FunList<B> = when(x) {
    is Cons -> {
      val (head, tail) = x
//      f(head) :: map(tail, f) // is this only valid in scala?
      Cons(f(head), map(tail, f))
    }
    else -> Nil
  }

  // Given a function from A -> Boolean, return a list with only those item where the function returned true.
  fun <A> filter(x: FunList<A>, f: (A) -> Boolean): FunList<A> = when(x) {
    is Cons -> {
      val (head, tail) = x
      if (f(head))
        Cons(head, filter(tail, f))
      else
        filter(tail, f)
    }
    else -> Nil
  }

  // This pattern should be familiar by now... psst... look at add.
  fun <A> append(x: FunList<A>, y: FunList<A>): FunList<A> = when(x) {
    is Cons -> {
      val (head, tail) = x
      Cons(head, append(tail, y))
    }
    else -> y
  }

  // Flatten a list of lists to a single list.  Remember you can't use list.flatten.  Can you use a previous
  // solution to solve this one?
  fun <A> flatten(x: FunList<FunList<A>>): FunList<A> = when(x) {
    is Cons -> {
      val (head, tail) = x
      append(head, flatten(tail))
    } else -> Nil
  }

  // Follow the types.  You've done a great job getting here. Follow the types.
  fun <A, B> flatMap(x: FunList<A>, f: (A) -> FunList<B>): FunList<B> =
    flatten(map(x, f))

  // Maximum of the empty list is 0
  fun maximum(x: FunList<Int>): Int = when(x) {
    is Cons -> {
      val (head, tail) = x
      val maxTail = maximum(tail)
      if (head >= maxTail) head else maxTail
    }
    else -> 0
  }

  // Reverse a list
  fun <A> reverse(x: FunList<A>): FunList<A> = when(x) {
    is Cons -> {
      val (head, tail) = x
      append(reverse(tail), Cons(head, Nil))
    }
    else -> Nil
  }
}
