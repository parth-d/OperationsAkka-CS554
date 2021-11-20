package actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import phones.{myPhone1, myPhone2, myPhone3}

object OrderProcessor {
  def props(phoneManufacturer: ActorRef): Props = Props(new OrderProcessor(phoneManufacturer))
}

class OrderProcessor(pManu: ActorRef) extends Actor {
  override def receive: Receive = {
    case order: Map[String, Int] =>
      println("Processing order")
      order.foreach(deliverable => 1 to deliverable._2 foreach(_ => pManu ! "Manufacture " + deliverable._1))
      println("sent orders")

    case _: myPhone1 =>
      println("Processed myPhone1")

    case _: myPhone2 =>
      println("Processed myPhone2")

    case _: myPhone3 =>
      println("Processed myPhone3")
  }
}

object Main extends App {
  val order = Map[String, Int]("myPhone1" -> 1, "myPhone2" -> 2, "myPhone3" -> 3)
  val system = ActorSystem("actorSystem")
  val phoneManufacturer: ActorRef     = system.actorOf(Props[PhoneManufacturer])
  val orderProcessor: ActorRef = system.actorOf(OrderProcessor.props(phoneManufacturer))
  println("Starting order")
  orderProcessor ! order
}

