package actors

import actors.OrderProcessor._
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Timers}
import akka.event.Logging
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import parser.{ActorParser, MessageParser, YAMLParser}
import phones.{myPhone1, myPhone2, myPhone3}

import java.io.FileReader
import scala.concurrent.duration.{Duration, FiniteDuration, MILLISECONDS}
import scala.language.postfixOps
import scala.reflect.runtime.universe
import scala.tools.reflect.ToolBox


object OrderProcessor {
//  snapshot message
  case object TakeSnapshot

  // Orders processed will change, hence var used
  var ordersProcessed: Int = 0

}

class OrderProcessor extends Actor with ActorLogging with Timers {

  // Actors required in our system
  // pManu: Phone manufacturer: Actor processing phones based on the model: {myPhone1, myPhone2, myPhone3}
  // actor: Order processor: Actor sending orders to pManu
  val pManu: ActorRef = context.actorOf(PhoneManufacturer.props(self), name="phone-manufacturer")
  val actor: ActorParser = Main.yaml.actors.find(c => c.name == "OrderProcessor").getOrElse(new ActorParser(null, null))

  //  Sends Recursive Snapshot Msg to capture Actors State in every 5 seconds
  timers.startTimerWithFixedDelay("capture-snapshot", TakeSnapshot, FiniteDuration(Duration("5 seconds").toMillis, MILLISECONDS))


  override def receive: Receive = {
    // Order received
    case order: Map[String, Int] =>
      val msg: MessageParser = actor.messages.find(m => m.name == "order").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      ordersProcessed = c(Map("pManu" -> pManu, "order" -> order, "log" -> log)).toInt

    // capture Actors State and sends recursive msg to child actors
    case TakeSnapshot =>
      val msg: MessageParser = actor.messages.find(m => m.name == "TakeSnapshot").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("pManu" -> pManu, "ordersProcessed" -> ordersProcessed))

    // Phone Manufacturer returned a manufactured myPhone1
    case _: myPhone1 =>
      val msg: MessageParser = actor.messages.find(m => m.name == "myPhone1").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("log" -> log))

    // Phone Manufacturer returned a manufactured myPhone2
    case _: myPhone2 =>
      val msg: MessageParser = actor.messages.find(m => m.name == "myPhone2").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("log" -> log))

    // Phone Manufacturer returned a manufactured myPhone3
    case _: myPhone3 =>
      val msg: MessageParser = actor.messages.find(m => m.name == "myPhone3").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("log" -> log))
  }
}

//Dynamic code Compiler
object Compiler {
  def compile[A](code: String): Map[String, Any] => A = {
    val tb = universe.runtimeMirror(getClass.getClassLoader).mkToolBox()
    val tree = tb.parse(
      s"""
         |def wrapper(context: Map[String, Any]): Any = {
         |  $code
         |}
         |wrapper _
      """.stripMargin)
    val wrapper = tb.compile(tree)()
    wrapper.asInstanceOf[Map[String, Any] => A]
  }

}

// Driver code
object Main extends App {
  // Initializations
  val system = ActorSystem("actorSystem")
  private val log = Logging(system, getClass.getName)

  //  Read YAML input file
  val reader = new FileReader("src/main/resources/input.YAML")
  val mapper = new ObjectMapper(new YAMLFactory())
  val yaml: YAMLParser = mapper.readValue(reader, classOf[YAMLParser])

  // creates two actors to create and process orders
  val orderProcessor: ActorRef = system.actorOf(Props[OrderProcessor], name="order-processor")
  val orderCreator:ActorRef = system.actorOf(OrderCreator.props(orderProcessor), name = "order-creator")

}

