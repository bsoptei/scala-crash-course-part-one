package definitions

// You can define interfaces via the trait keyword
// Traits can have abstract and concrete members
trait PresentValue {
  def presentValue: Either[String, Double]
  def presentValueOption: Option[Double] = presentValue.toOption
}