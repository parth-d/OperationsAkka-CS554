package actors

import actors.OrderProcessor.TakeSnapshot
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Timers}
import akka.event.Logging
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import parser.{ActorParser, MessageParser, YAMLParser}
import phones.{myPhone1, myPhone2, myPhone3}

import java.io.FileReader
import scala.concurrent.duration.{Duration, DurationInt, FiniteDuration, MILLISECONDS}
import scala.language.postfixOps
import scala.reflect.runtime.universe
import scala.tools.reflect.ToolBox


object OrderProcessor {
//  def props(phoneManufacturer: ActorRef): Props = Props(new OrderProcessor(phoneManufacturer))
  case object TakeSnapshot

}

class OrderProcessor extends Actor with ActorLogging with Timers{

  val pManu: ActorRef = context.actorOf(PhoneManufacturer.props(self))
  val actor : ActorParser = Main.yaml.actors.find(c => c.name=="OrderProcessor" ).getOrElse(new ActorParser(null,null))

//  send snapshot msg in every 5 seconds
  timers.startTimerWithFixedDelay("capture-snapshot", TakeSnapshot, FiniteDuration(Duration("5 seconds").toMillis, MILLISECONDS))

  override def receive: Receive = {

    case order: Map[String, Int] =>
      val msg: MessageParser = actor.messages.find(m => m.name == "order").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("pManu"->pManu, "order"-> order, "log"->log))
    case TakeSnapshot =>
      val msg: MessageParser = actor.messages.find(m => m.name == "TakeSnapshot").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("pManu"->pManu))
    case _: myPhone1 =>
      val msg: MessageParser = actor.messages.find(m => m.name == "myPhone1").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("log"->log))
    case _: myPhone2 =>
      val msg: MessageParser = actor.messages.find(m => m.name == "myPhone2").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("log"->log))
    case _: myPhone3 =>
      val msg:MessageParser = actor.messages.find(m=> m.name == "myPhone3").getOrElse(new MessageParser(null,null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("log"->log))
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

object Main extends App {
  var ordersCreated: Int = 0
  def createOrder():Map[String, Int] = {
    ordersCreated += 1
    Map[String, Int]("myPhone1" -> (Math.random() * 10).toInt, "myPhone2" -> (Math.random() * 10).toInt, "myPhone3" -> (Math.random() * 10).toInt, "orderNumber" -> ordersCreated)
  }

  //  val order = Map[String, Int]("myPhone1" -> 2, "myPhone2" -> 2, "myPhone3" -> 2)
  val system = ActorSystem("actorSystem")
  private val log = Logging(system, getClass.getName)

  //  Read YAML input file
  val reader = new FileReader("src/main/resources/input.YAML")
  val mapper = new ObjectMapper(new YAMLFactory())
  val yaml: YAMLParser = mapper.readValue(reader, classOf[YAMLParser])

  log.info("Creating orders continuously.")
  val orderProcessor: ActorRef = system.actorOf(Props[OrderProcessor])
  log.info("Sending orders")
  val deadline = 5.seconds.fromNow
  while (deadline.hasTimeLeft){
    orderProcessor ! createOrder()
    Thread.sleep((math.random() * 100).toInt)
  }
}


