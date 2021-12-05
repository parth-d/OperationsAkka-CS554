import actors.OrderProcessor
import akka.actor.{ActorSystem}
import akka.testkit.{EventFilter, InfoFilter, TestEvent, TestProbe}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import parser.YAMLParser

import java.io.FileReader

class OrderProcessorSpec extends AnyWordSpec with Matchers {

  implicit val system = ActorSystem()
  system.eventStream.publish(TestEvent.Mute(EventFilter.info()))

  "Creating OrderProcessor" should {
    "result in logging Order Processor Created in info" in {
      EventFilter.info(pattern = "Order Processor Created", occurrences = 1) intercept {
        val reader = new FileReader("src/main/resources/input.YAML")
        val mapper = new ObjectMapper(new YAMLFactory())
        val yaml: YAMLParser = mapper.readValue(reader, classOf[YAMLParser])
        system.actorOf(OrderProcessor.props(yaml) , name="orderprocessor")
      }
    }
  }
}
