package monoids

import cats._
import cats.implicits._

object SuperAdder extends App {
  def add[A](items: List[A])(implicit monoid: Monoid[A]): A = {
    items.fold(monoid.empty)((a, b) => monoid.combine(a, b))
  }

  println(add((1 until 10).toList))
  println(add((1 until 10).map(Option(_)).toList))

  val orderA = Order(10, 1)
  val orderB = Order(20, 2)
  println(Monoid[Order].combine(orderA, orderB))
}
