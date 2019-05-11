package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
	trait TestTrees {
		val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
		val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
	}


  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("makeCodeTree1") {
    assert(makeCodeTree(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('c',2)) ===
      Fork(
        Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5),
        Leaf('c',2),
        List('a','b','c'),
        7
      )
    )
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times1") {
    assert(times(List('a', 'b', 'a')) ===
      List(('a', 2), ('b', 1)))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("singleton") {
    assert(singleton(List(Leaf('t', 2))) === true)
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("combine2") {
    val list = List(Leaf('a', 1), Leaf('b',1), Leaf('c',1))
    assert(combine(list) === List(Leaf('c',1)), Fork(Leaf('a',1), Leaf('b', 1), List('a', 'b'), 2))
  }

  test("combine3") {
    val list = List(Fork(Leaf('a',1), Leaf('b', 1), List('a', 'b'), 2), Leaf('c',1))
    combine(list)
    assert(combine(list) === List(Fork(Leaf('a',1), Leaf('b', 1), List('a', 'b'), 2), Leaf('c',1)))
  }

  test("until1") {
    new TestTrees {
      assert(until(singleton, combine)(List(Leaf('a',2), Leaf('b',3))) === List(t1))
    }
  }

  test("until2") {
    new TestTrees {
      assert(until(singleton, combine)(List(Leaf('a',1), Leaf('b',1), Leaf('c', 1))) ===
        List(Fork(Fork(Leaf('b',1), Leaf('c',1), List('a','b'), 2), Leaf('a', 1), List('a', 'b', 'c'), 3))
      )
    }
  }

  test("createCodeTree") {
//    val a = createCodeTree(List('a', 'b', 'c'))
//    assert(true)
  }

  test("decodeSecret") {
    assert(decode(frenchCode, secret) === "huffmanestcool".toList)
  }

  test("encode1") {
    new TestTrees {
      assert(encode(t1)("ab".toList) === List(0, 1))
    }
  }

  test("decode1") {
    new TestTrees {
      assert(decode(t1, List(0, 1)) === "ab".toList)
    }
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("codeBits") {
    assert(codeBits(List(('a', List(0,1))))('a') ===  List(0, 1))
  }

  test("convert") {
    new TestTrees {
      assert(convert(t1) === List(('a', List(0)), ('b', List(1))))
    }
  }

  test("mergeCodeTables") {
    assert(
      mergeCodeTables(
        List(('a', List(0)), ('b', List(1))),
        List(('c', List(0, 1)))
      ) ===
      List(('a', List(0)), ('b', List(1)), ('c', List(0, 1)))
    )
  }

  test("quickencode1") {
    new TestTrees {
      assert(quickEncode(t1)("ab".toList) === List(0, 1))
    }
  }

}
