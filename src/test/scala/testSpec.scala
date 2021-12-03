import actors.OrderCreator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class testSpec extends AnyFlatSpec with Matchers{
  behavior of "parameters"
  it should "obtain type of orders" in {OrderCreator.numberOfOrders shouldBe a [Int]}
  it should "obtain number of orders" in {OrderCreator.numberOfOrders should be > 0}
  it should "obtain the delay between orders" in {OrderCreator.orderDelay should be (5000)}
}