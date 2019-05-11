package monads

import cats._
import cats.implicits._
import cats.data._

object Writer {
  type Logged[A] = Writer[Vector[String], A]

  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    val ans = slowly(if(n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }

  def wFactorial(n: Int): Logged[Int] = {
    /*if (n == 0) {
      slowly(1).writer(Vector(s"fact $n 1"))
    } else {
      for {
        a <- n.writer(Vector(s"fact $n ?"))
        b <- wFactorial(n -1)
      } yield slowly(a * b)
    }*/

    def calculateFactorialValue(n: Int): Logged[Int] = {
      if(n == 0) {
        slowly(1.pure[Logged])
      } else {
        slowly(wFactorial(n - 1).map(_ * n))
      }
    }

    for {
      v <- calculateFactorialValue(n)
      _ <- Vector(s"fact $n $v").tell
    } yield v
  }
}
