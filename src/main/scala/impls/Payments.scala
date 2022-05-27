package impls

import definitions._
import utils._

// Sealed traits can only be extended in the source where they are defined
// This way the number of children is finite and known
// They provide an alternative to enums
sealed trait Payments
// Case classes are used as immutable containers of data
// They have an automatically generated factory method, and all fields are public and read-only by default
// Plus they have other useful features
// https://docs.scala-lang.org/tour/case-classes.html
// The final keyword makes a class non-extendable
// Case classes are meant to be final
// https://en.wikipedia.org/wiki/Perpetuity
final case class Perpetuity(amount: Double, discountRate: Double)
  // You can extend a class and multiple traits
  extends SingleDiscountRate(discountRate) with Payments {
    // The override keyword makes it explicit that a member is overridden
    override def presentValue: Either[String, Double] = safeDivide(amount, discountRate)
  }

// https://en.wikipedia.org/wiki/Annuity
final case class Annuity(amount: Double, discountRate: Double, numberOfPeriods: Int) 
  extends SingleDiscountRate(discountRate) with Payments {
    override def presentValue: Either[String, Double] = 
      // The map higher order function applies a function for all elements of a collection or wrapper
      discountFactor(discountRate / 12, numberOfPeriods * 12).map(_ * amount)
  }

// Case objects have additional features compared to regular objects
// https://docs.scala-lang.org/overviews/scala-book/case-objects.html
case object ZeroPayment extends Payments with PresentValue {
  override def presentValue: Either[String, Double] = Right(0.0)
}