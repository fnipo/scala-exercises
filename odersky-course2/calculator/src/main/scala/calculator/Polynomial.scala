package calculator

import scala.math._

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal {
      pow(b(), 2) - (4 * a() * c())
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {

    def computeVariant(rootDelta: Double)(f: (Double, Double) => Double) = {
      f(rootDelta, b()) / 2 * a()
    }

    val rootDelta = sqrt(delta())
    Signal {
      if (rootDelta < 0) {
        Set.empty
      } else {
        Set(
            computeVariant(rootDelta)((rootDelta, b) => -(rootDelta + b)),
            computeVariant(rootDelta)((rootDelta, b) => (rootDelta - b))
        )
      }
    }
  }
}
