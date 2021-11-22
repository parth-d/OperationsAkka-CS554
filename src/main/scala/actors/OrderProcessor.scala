package actors

import actors.OrderProcessor.TakeSnapshot
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Timers}
import akka.event.Logging
import phones.{myPhone1, myPhone2, myPhone3}

import scala.collection.parallel.CollectionConverters.{MapIsParallelizable, seqIsParallelizable}
import scala.concurrent.duration.{Duration, FiniteDuration, MILLISECONDS}
import scala.language.postfixOps

object OrderProcessor {
  def props(phoneManufacturer: ActorRef): Props = Props(new OrderProcessor(phoneManufacturer))
  case object TakeSnapshot
}

class OrderProcessor(pManu: ActorRef) extends Actor with ActorLogging with Timers{

  timers.startTimerWithFixedDelay("capture-snapshot", TakeSnapshot, FiniteDuration(Duration("5 seconds").toMillis, MILLISECONDS))
//  timers.startSingleTimer("capture", TakeSnapshot, FiniteDuration(Duration("5 seconds").toMillis, MILLISECONDS) )
  override def receive: Receive = {
    case order: Map[String, Int] =>
//      println("Processing order")
      order.par.foreach(deliverable => (1 to deliverable._2 toList).par foreach(_ => pManu ! "Manufacture " + deliverable._1))

      log.info("sent orders")
    case TakeSnapshot => pManu ! TakeSnapshot

    case _: myPhone1 =>
      log.info("Processed myPhone1")

    case _: myPhone2 =>
      log.info("Processed myPhone2")

    case _: myPhone3 =>
      log.info("Processed myPhone3")
  }
}

object Main extends App {
  val order = Map[String, Int]("myPhone1" -> (Math.random() * 5).toInt, "myPhone2" -> (Math.random() * 5).toInt, "myPhone3" -> (Math.random() * 5).toInt)
  val system = ActorSystem("actorSystem")
  private val log = Logging(system, getClass.getName)
  log.info("Current order: " + order)
  val phoneManufacturer: ActorRef     = system.actorOf(Props[PhoneManufacturer])
  val orderProcessor: ActorRef = system.actorOf(OrderProcessor.props(phoneManufacturer))
  log.info("Starting order")
  orderProcessor ! order
}

