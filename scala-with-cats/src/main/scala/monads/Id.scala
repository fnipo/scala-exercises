package monads

import cats._

object Id {
  implicit val idMonad: Monad[Id] = new Monad[Id] {
    override def pure[A](a: A): Id[A] = a

    override def flatMap[A, B](value: Id[A])(f: A => Id[B]): Id[B] = f(value)

    // not needed
    override def map[A, B](value: Id[A])(f: A => B): Id[B] = f(value)
  }
}
