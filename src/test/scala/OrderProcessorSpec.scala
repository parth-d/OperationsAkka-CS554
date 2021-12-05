import actors.OrderProcessor
import akka.actor.{ActorIdentity, ActorRef, ActorSystem, Identify}
import akka.testkit.{EventFilter, InfoFilter, TestEvent, TestProbe}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import org.scalatest.wordspec.AnyWordSpec
import parser.YAMLParser

import java.io.FileReader
import scala.concurrent.duration.FiniteDuration
import scala.language.postfixOps

class OrderProcessorSpec extends AnyWordSpec with Matchers {

//  Used this code from Akka Training code
  implicit class TestProbeOps(probe: TestProbe) {
    def expectActor(path: String, max: FiniteDuration = 3.seconds): ActorRef = {
      probe.within(max) {
        var actor = null: ActorRef
        probe.awaitAssert {
          (probe.system actorSelection path).tell(Identify(path), probe.ref)
          probe.expectMsgPF(100 milliseconds) {
            case ActorIdentity(`path`, Some(ref)) => actor = ref
          }
        }
        actor
      }
    }
  }


  implicit val system = ActorSystem()
  system.eventStream.publish(TestEvent.Mute(EventFilter.info()))

  "Creating OrderProcessor" should {
    val reader = new FileReader("src/main/resources/input.YAML")
    val mapper = new ObjectMapper(new YAMLFactory())
    val yaml: YAMLParser = mapper.readValue(reader, classOf[YAMLParser])
    "result in logging Order Processor Created at info" in {
      EventFilter.info(pattern = "Order Processor Created") intercept {
        system.actorOf(OrderProcessor.props(yaml) , name="order-processor")
      }
    }
    "result in creating child actor with name phone-manufacturer" in {
      system.actorOf(OrderProcessor.props(yaml) , name="child-phone-manufacturer")
      TestProbe().expectActor("/user/child-phone-manufacturer/phone-manufacturer")
    }
//    "result in getting TakeSnapshot message" in {
//      val orderProcessor = system.actorOf(OrderProcessor.props(yaml) , name="order-processor-snapshot")
//      TestProbe().expectMsg(max = 20.seconds, OrderProcessor.TakeSnapshot)
//    }
    "result in logging 'Processing Received order :3' at info when order message is sent to orderProcessor" in {
      EventFilter.info(pattern = "Processing Received order :3") intercept {
        val orderProcessor = system.actorOf(OrderProcessor.props(yaml) , name="order-processor-getOrder")
        orderProcessor ! Map[String, Int]("myPhone1" -> 2, "myPhone2" -> 2, "myPhone3" -> 2, "orderNumber" -> 3)
      }
    }
  }
}
