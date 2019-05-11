package monads

import cats._
import cats.implicits._
import cats.data._

object Calculator {
  type CalcState[A] = State[List[Int], A]

  def operand(num: Int): State[List[Int], Int] = {
    State[List[Int], Int] { stack => (num :: stack, num) }
  }

  def operator(f: (Int, Int) => Int): State[List[Int], Int] = {
    State[List[Int], Int] {
      case b :: a :: tail =>
        val result = f(a, b)
        (result :: tail, result)
      case _ =>
        sys.error("Fail!")
    }
  }

  def evalOne(sym: String): State[List[Int], Int] = {
    sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case number => operand(number.toInt)
    }
  }

  def evalAll(expr: List[String]): State[List[Int], Int] = {
//    expr match {
//      case head :: Nil => evalOne(head)
//      case head :: tail => {
//        for {
//          _ <- evalOne(head)
//          ans <- evalAll(tail)
//        } yield ans
//      }
//      case _ => 0.pure[CalcState]
//    }

    // from book
    expr.foldLeft(0.pure[CalcState]) { (a, b) =>
      a.flatMap(_ => evalOne(b))
    }
  }

}
