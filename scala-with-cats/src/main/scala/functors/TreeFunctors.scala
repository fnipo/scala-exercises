package functors

import cats._
import cats.implicits._

object TreeFunctors {
  // When looking for Tree

  implicit def treeFunctor: Functor[Tree] =
    new Functor[Tree] {
      override def map[A, B](src: Tree[A])(f: A => B): Tree[B] = {
        src match {
          case Branch(left, right) =>
            Branch(map(left)(f), map(right)(f))
          case Leaf(value) =>
            Leaf(f(value))
        }
      }
    }

  /*
    TODO: Is there a way to make code below work (Functor[Tree] is not found in code below)?
    Implicit search is covariant Functor[+A] and should accept a subtype definition of Functor[Branch]
    or Functor[Leaf] as possible candidates on the absence of a Functor[Tree] definition
   */
//  implicit def branchFunctor(implicit treeFunctor: Functor[Tree]): Functor[Branch] =
//    new Functor[Branch] {
//      override def map[A, B](src: Branch[A])(f: A => B): Branch[B] = {
//        Branch(
//          treeFunctor.map(src.left)(f),
//          treeFunctor.map(src.right)(f)
//        )
//    }
//  }
//
//  implicit def leafFunctor: Functor[Leaf] =
//    new Functor[Leaf] {
//      override def map[A, B](src: Leaf[A])(f: A => B): Leaf[B] = {
//        (f map Leaf.apply)(src.value)
//      }
//  }
}
