package actors

import akka.actor.{Actor, ActorRef, Props}
import components.body.{B1, B2, B3}
import components.camera.C1
import components.processor.P1
import phones.{myPhone1, myPhone2, myPhone3}

class PhoneManufacturer extends Actor{
  override def receive: Receive = {
    case "Manufacture myPhone1" =>
      println("Received order to process myPhone1")
      sender() ! new myPhone1(new P1(), new C1, new B1)

    case "Manufacture myPhone2" =>
      println("Received order to process myPhone2")
      sender() ! new myPhone2(new P1(), new C1, new B2)

    case "Manufacture myPhone3" =>
      println("Received order to process myPhone3")
      sender() ! new myPhone3(new P1(), new C1, new B3)

  }
}
