package calculator

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import org.scalatest._

@RunWith(classOf[JUnitRunner])
class PolynomialSuite extends FunSuite with ShouldMatchers {

  test("computeDelta") {
    val result = Polynomial.computeDelta(Signal(1), Signal(2), Signal(3))()
    assert(result == -8)
  }

  test("computeSolutions") {
    var a = Signal(2.0)
    var b = Signal(3.0)
    var c = Signal(1.0)
    val result = Polynomial.computeSolutions(a, b, c, Polynomial.computeDelta(a, b, c))()
    assert(result == Set(-2, -4))
  }

}
