package monoids

import cats.Monoid

object SetMonoids {
  implicit def union[A]: Monoid[Set[A]] =
    new Monoid[Set[A]] {
      override def empty: Set[A] = Set()
      override def combine(x: Set[A], y: Set[A]): Set[A] = x ++ y
  }

  // Intersect is not associative, doesn't comply with monoids rules
//  implicit def intersect[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
//    override def empty: Set[A] = Set()
//    override def combine(x: Set[A], y: Set[A]): Set[A] = x intersect y
//  }
}
