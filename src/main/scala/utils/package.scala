import definitions._
import impls._

// Package objects can be defined in any package
// You can use them for common members for the package
package object utils {
  // You can use the type keyword to define type aliases
  // It is similar to val, but at the type level
  // The with keyword is used to combine types
  type PaymentsWithPV = Payments with PresentValue

  // Either is used to preserve the information about calculation error, if any
  def safeDivide(numerator: Double, denominator: Double): Either[String, Double] =
    if (denominator != 0) Right(numerator / denominator)
    else Left("Denominator is 0, which doesn't make sense!!!!!")
}