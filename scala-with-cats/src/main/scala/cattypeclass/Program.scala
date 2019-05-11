package cattypeclass

import cats._
import cats.implicits._

object Program extends App {
  // Show
  Show.apply[Int]
  println("Felipe" show)

  val cat1 = Cat("Homer", 40, "Ginger")
  println(cat1.show)


  // Eq
  val cat2 = Cat("Garfield", 38, "orange and black")
  val cat3 = Cat("Heathcliff", 33, "orange and black")

  println (Option(cat2) === Option.empty[Cat])
}
