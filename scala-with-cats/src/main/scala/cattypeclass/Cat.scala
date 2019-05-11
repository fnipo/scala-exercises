package cattypeclass

import cats._

final case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit val catShow: Show[Cat] = {
    Show.show(c => s"${c.name} is a ${c.age} year-old ${c.color} cat.")
  }
  implicit val catEq: Eq[Cat] = {
    Eq.instance[Cat] { (a, b) => a.age > b.age }
  }
}