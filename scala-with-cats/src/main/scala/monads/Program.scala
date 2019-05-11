package monads

import cats._
import cats.implicits._
import monads.StackSafety.foldRight
import functors._

import cats.syntax.functor._ // for map
import cats.syntax.flatMap._ // for flatMap

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Program extends App {
  val foldResult = foldRight((1 to 100000).toList, 0L)(_ + _)
  println(foldResult.value)


  /* WRITERS */
//  val r = Await.result(Future.sequence(Vector(
//    Future(Writer.factorial(3)),
//    Future(Writer.factorial(3))
//  )), 5.seconds)
//  println(r)

  val r2 = for {
    a <- Writer.wFactorial(3)
    b <- Writer.wFactorial(3)
  } yield a + b
  r2.written.foreach(println(_))


  /* READERS */
  val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )
  val passwords = Map(
    "dade" -> "zerocool",
    "kate" -> "acidburn",
    "margo" -> "secret"
  )

  val db = Db(users, passwords)
  println("checkLogin1: " + Db.checkLogin(1, "zerocool").run(db))
  println("checkLogin2: " + Db.checkLogin(4, "davinci").run(db))


  /* STATE */
  println(Calculator.evalOne("42").runA(Nil).value)

  val program = for {
    _ <- Calculator.evalAll(List("1", "2", "+"))
    _ <- Calculator.evalAll(List("3", "4", "+"))
    ans <- Calculator.evalOne("*")
  } yield ans
  println(program.runA(Nil).value)


  /* CUSTOM */
  for {
    a <- Branch(Leaf(100), Leaf(200))
    b <- Branch(Leaf(a - 10), Leaf(a + 10))
    c <- Branch(Leaf(b - 1), Leaf(b + 1))
  } yield c
}
