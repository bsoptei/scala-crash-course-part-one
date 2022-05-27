package impls

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

// Scalatest is a framework for unit testing
// AnyFunSuite is one of many styles you can write your tests in
// See https://www.scalatest.org/
class PaymentsTest extends AnyFunSuite with Matchers {
  import PaymentsTest._

  test("Annuity PV") {
    val a = Annuity(100d, 0.12, 5)
    a.presentValueOption.map { pv =>
      pv shouldBe 4495.50384 +- Tolerance
    }
  }

  test("Perpetuity PV") {
    val p = Perpetuity(100, 0.05)
    p.presentValueOption.map { pv =>
      pv shouldBe 2000.0 +- Tolerance
    }
  }

  test("ZeroPayment PV") {
    ZeroPayment.presentValue.map { pv =>
      pv shouldBe 0.0
    }
  }
}

object PaymentsTest {
  private final val Tolerance = 1e-5
}