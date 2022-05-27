package utils

import impls._

// Classes are defined via the class keyword
// The default constructor is defined at the declaration
// The val keyword here makes the arguments public read-only fields of the class
// Omitting the val would result in the field being private
// Iterable represents a generic collection
// https://docs.scala-lang.org/overviews/collections-2.13/introduction.html
class PresentValueCalculator(val payments: Iterable[PaymentsWithPV]) {
  // If a val is lazy, it is calculated only the first time it is accessed, then its value is cached
  // The private keyword makes members private
  private lazy val presentValues = payments.flatMap(_.presentValueOption)

  // The Option type represents the presence (Some) or missing (None) of a value
  // It is a safer way to say something may or may not be there instead of using null values
  def totalPresentValue: Option[Double] = 
    // The foldLeft higher order function is a means of aggregation, along with fold and other variants
    // foldLeft is a good example for multiple argument lists, more on that later
    // For summing values, there is also the sum method as a shorthand
    if (payments.nonEmpty) Some(presentValues.foldLeft(0.0)((acc, current) => acc + current))
    else None
}

// You can define a so-called companion object for any class
// It usually contains static members related to the class
object PresentValueCalculator {
  // The apply method is an idiomatic way to define factory methods
  // It has a syntactic sugar feature, you can call it using the object name and the arguments in parentheses
  // The asterisk represents vararg
  def apply(payments: PaymentsWithPV*): PresentValueCalculator = new PresentValueCalculator(payments)
}