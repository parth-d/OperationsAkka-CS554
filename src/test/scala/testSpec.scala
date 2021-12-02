import actors.OrderProcessor
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class testSpec extends AnyFlatSpec with Matchers{
  behavior of "parameters"
  it should "obtain type of orders" in {OrderProcessor.numberOfOrders shouldBe a [Int]}
  it should "obtain number of orders" in {OrderProcessor.numberOfOrders should be > 0}
  it should "obtain the delay between orders" in {OrderProcessor.orderDelay should be (500)}
}