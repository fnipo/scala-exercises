package monads

import functors._

import scala.annotation.tailrec

object TreeMonad {
  implicit val treeMonad = new Monad[Tree] {
    override def pure[A] (a: A): Tree[A] = {
      Leaf(a)
    }

    override def flatMap[A, B] (value: Tree[A] ) (f: A => Tree[B] ): Tree[B] = value match {
      case Branch (left, right) => Branch (
        flatMap (left) (f),
        flatMap (right) (f)
      )
      case Leaf (value) => f (value)
    }

    // book implementation. How does it work?
//    def tailRecM[A, B](arg: A)(func: A => Tree[Either[A, B]]): Tree[B] = {
//      @tailrec
//      def loop(open: List[Tree[Either[A, B]]], closed: List[Option[Tree[B]]]): List[Tree[B]] = open match {
//        case Branch(l, r) :: next =>
//          loop(l :: r :: next, None :: closed)
//        case Leaf(Left(value)) :: next =>
//          loop(func(value) :: next, closed)
//        case Leaf(Right(value)) :: next =>
//          loop(next, Some(pure(value)) :: closed)
//        case Nil =>
//          closed.foldLeft(Nil: List[Tree[B]]) { (acc, maybeTree) =>
//            maybeTree.map(_ :: acc).getOrElse {
//              val left :: right :: tail = acc
//              Branch(left, right) :: tail
//            }
//          }
//      }
//
//      loop(List(func(arg)), Nil).head
//    }
  }
}
