package monads

import cats._

object StackSafety {
  def foldRight[A, B](as: List[A], acc: B)(f: (A, B) => B): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(foldRight(tail, acc)(f)) map(f(head, _))
      case Nil =>
        Eval.now(acc)
    }
}
