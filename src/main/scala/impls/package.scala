import utils._

package object impls {
  // This is a function
  // Functions are first class citizens, they can be defined and passed around like any other value
  // Furthermore, this is a pure function https://en.wikipedia.org/wiki/Pure_function
  // It's visibility is package private
  private[impls] val discountFactor = 
    (discountRate: Double, numberOfPeriods: Int) => safeDivide(1 - math.pow(1 + discountRate, -numberOfPeriods), discountRate)
}