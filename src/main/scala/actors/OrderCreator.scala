package actors

import actors.OrderCreator._
import akka.actor.{Actor, ActorLogging, ActorRef, Props, Timers}

object OrderCreator{
  def props(orderProcessor: ActorRef): Props = Props(new OrderCreator(orderProcessor))
  var orderCreated = 0;

  val orderDelay: Int = 5000
  val numberOfOrders: Int = 20
}

class OrderCreator(orderProcessor: ActorRef) extends Actor with ActorLogging with Timers{
  override def receive: Receive = {
    case _ => log.info("Didn't expect any message")
  }

  while (orderCreated <= numberOfOrders) {
    orderProcessor ! order()
    Thread.sleep((math.random() * orderDelay).toInt)
  }

  // Function which generates an order and manages the count
  def order(): Map[String, Int] = {
    orderCreated += 1
    println((math.random() * orderDelay).toInt)
    Map[String, Int]("myPhone1" -> (Math.random() * 10).toInt, "myPhone2" -> (Math.random() * 10).toInt, "myPhone3" -> (Math.random() * 10).toInt, "orderNumber" -> orderCreated)
  }
}
