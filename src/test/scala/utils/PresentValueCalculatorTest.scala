package utils

import impls._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class PresentValueCalculatorTest extends AnyFunSuite with Matchers {
  import PresentValueCalculatorTest._

  test("totalPV") {
    // Here, we use the factory method with varargs 
    val pvCalc = PresentValueCalculator(Annuity(100d, 0.12, 5), Perpetuity(100, 0.05))
    pvCalc.totalPresentValue.map { pv =>
      pv shouldBe 6495.50384 +- Tolerance
    }
    // Here, we access the public read-only member
    pvCalc.payments.size shouldBe 2
  }
}

object PresentValueCalculatorTest {
  private final val Tolerance = 1e-5
}