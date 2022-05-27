// You can structure your code using packages
package main

// You can import things by explicit names or use underscores as wildcards
import definitions._
import impls._
import utils._
import scala.io.StdIn._
import scala.util.{Failure, Success, Try}

// You can define singleton objects using the object keyword
// There are two main ways to define an entry point
// One is to extend App using an object, in this case you write the program in the object body
// Another way is to define a main method in an object and initiate the logic inside 
// See https://docs.scala-lang.org/overviews/scala-book/hello-world-1.html
object Main extends App {
  // Imports can be used basically anywhere
  import Helpers._

  println("How many payments would you like to process?")
  // You can define values by the val keyword
  // These are fixed and cannot be modified
  val numberOfPayments = 
    // Try is a wrapper for error handling
    // It has to variants, Success and Failure, from which the value or error can be extracted
    Try(readInt())

  // Pattern matching is a powerful feature in Scala
  // You can match on type variants, types, values, and much more
  // https://docs.scala-lang.org/tour/pattern-matching.html
  numberOfPayments match {
    // You can create bindings and define guards
    case Success(n) if n > 0 =>
      // The for comprehension is another powerful feature
      // You can use it to process sequences of data
      // https://docs.scala-lang.org/tour/for-comprehensions.html
      // You can define number ranges with to (inclusive) or until (exclusive)
      // With the yield keyword, you can create a collection of data
      val payments = for (i <- 1 to n) yield readPayment(i, n)

      // Higher order functions take functions as arguments or return functions
      // The flatMap higher order function applies a function to all elements of a collection or wrapper and flattens the structure
      // Here, an anonymous function is passed as an argument
      val goodPayments = payments.flatMap(payment => payment.toOption)

      // There is also the classic boolean based control flow for boolean hearts
      if (goodPayments.size == payments.size) {
        // One way to create instances is using the new keyword
        val pvCalc = new PresentValueCalculator(goodPayments)
        val totalPV = pvCalc.totalPresentValue

        // You can create multiline strings with triple quotes
        // The s prefix gives you a string interpolator, inside which you can insert expressions with $ or ${}
        println(s"""Your total present value is:
          $totalPV
        """)
      } else {
        // The filter higher order function filters a collection or wrapper based on a predicate
        // The underscore here is syntactic sugar for defining anonymous functions
        val errors = payments.filter(_.isLeft).flatMap(_.left.toOption).toSet

        println("Found the following errors:")
        // The foreach higher order function can be applied to do side effects on elements of a collection or wrapper
        // It returns Unit, which is similar to void
        errors foreach println
      }
    case Success(n) => println(s"Sorry, can't work with $n.")
    case Failure(_) => println(ParseErrorMessage)
  }
}

object Helpers {
  // Final vals are constants
  // Members in objects are static
  // If no explicit visibility modifier is provided, members are public by default
  final val ParseErrorMessage: String = "Could not parse your input as numeric value."

  // You can define methods with the def keyword
  // Either represents a successful (Right) or a failed (Left) computation; Left can contain other things than exceptions
  def readPayment(round: Int, total: Int): Either[String, PaymentsWithPV] = {
    println(s"$round of $total...")
    println("Annuity(a)/Perpetuity(p)")
    val choice = readLine()

    // Using pattern matching to find concrete values
    choice.toLowerCase match {
      case "a" =>
        println("Annuity amount:")
        val amountTry = Try(readDouble())

        println("Annuity discount rate:")
        val discountRateTry = Try(readDouble())

        println("Annuity number of periods:")
        val numberOfPeriodsTry = Try(readInt())

        // Tuples can hold multiple values and can be used in pattern matching and can also be destructured
        // They are also useful if you want to return multiple values from a function or method
        // https://docs.scala-lang.org/tour/tuples.html
        (amountTry, discountRateTry, numberOfPeriodsTry) match {
          case (Success(amount), Success(discountRate), Success(numberOfPeriods)) =>
            // Some classes have factory methods so you can omit the new keyword when instantiating them (see more there)
            Right(Annuity(amount, discountRate, numberOfPeriods))
          // Pattern matching should be exhaustive
          // Here, the underscore is a wildcard representing all other cases
          case _ => Left(ParseErrorMessage)
        }
      case "p" => 
        println("Perpetuity amount:")
        val amountTry = Try(readDouble())

        println("Perpetuity discount rate:")
        val discountRateTry = Try(readDouble())

        (amountTry, discountRateTry) match {
          case (Success(amount), Success(discountRate)) => 
            Right(Perpetuity(amount, discountRate))
          case _ => Left(ParseErrorMessage)
        }      
      // This time, the all other cases case is bound to a name because its value is used
      case other => Left(s"Don't know what you mean by $other.")
    }
  }
}