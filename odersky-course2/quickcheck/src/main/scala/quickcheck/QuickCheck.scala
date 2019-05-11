package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = {
    //oneOf(
      //const(empty),
      for {
        k <- arbitrary[A]
        m <- oneOf(const(empty), genHeap)
      } yield insert(k, m)
    //)
  }

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  // If you insert any two elements into an empty heap, finding the minimum of the resulting heap should get the smallest of the two elements back
  property("min2") = forAll { (a: Int, b: Int) =>
    val h = insert(a, insert(b, empty))
    findMin(h) == (if (a < b) a else b)
  }

  // If you insert an element into an empty heap, then delete the minimum, the resulting heap should be empty.
  property("delete turning it empty") = forAll { a: Int =>
    val h = insert(a, empty)
    deleteMin(h) == empty
  }

  property("delete min 2 elems results empty") =
    forAll { (a: Int, b: Int, c: Int, d: Int) =>
      val h1 = insert(a, insert(b, empty))
      deleteMin(deleteMin(h1)) == empty
    }

  // Given any heap, you should get a sorted sequence of elements when continually finding and deleting minima. (Hint: recursion and helper functions are your friends.)
  property("sorted when deleting minimum") =
    forAll { (h: H) =>
      val seq = elemSeq(h, Seq())
      seq == seq.sorted
    }

  // Finding a minimum of the melding of any two heaps should return a minimum of one or the other
  property("min meld is min of one or another") =
    forAll { (h1: H, h2: H) =>
      val hm = meld(h1, h2)
      val m = Math.min(findMin(h1), findMin(h2))
      val fm = findMin(hm)
      fm == m
    }

  property("melding 3 times and deleting 3 mins, next min are equal") =
    forAll { (h: H) =>
      val hm = meld(meld(h, h), h)
      val h1 = deleteMin(deleteMin(deleteMin(hm)))
      val h2 = deleteMin(h)
      isEmpty(h2) || findMin(h1) == findMin(h2)
    }

  property("meld empty results same heap") =
    forAll { (h: H) =>
      meld(h, empty) == h
    }

  property("meld empty results same heap") =
    forAll { (h: H) =>
      meld(empty, h) == h
    }


  def elemSeq(h: H, s: Seq[A]): Seq[A] = {
    if (isEmpty(h)) s
    else elemSeq(deleteMin(h), s ++ Seq(findMin(h)))
  }
}