package functors

import cats._
import cats.implicits._
import TreeFunctors._
import CodecInstances._

object Program extends App {
  val tree = Branch(Branch(Leaf(10), Leaf(20)), Leaf(30))
  val newTree = Functor[Tree].map(tree)(x => x * 10)
  println(newTree)


}
