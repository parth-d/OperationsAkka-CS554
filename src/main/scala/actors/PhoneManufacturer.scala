package actors

//import actors.OrderProcessor.TakeSnapshot
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import parser.{ActorParser, MessageParser}

import java.io.FileWriter
import scala.beans.BeanProperty
import scala.concurrent.duration.{FiniteDuration, MILLISECONDS}

object PhoneManufacturer {
  def props(orderProcessor: ActorRef): Props = Props(new PhoneManufacturer(orderProcessor))
  case class TakeSnapshot(ordersProcessed: Int)
}

class PhoneManufacturer(orderProcessor: ActorRef) extends Actor with ActorLogging {

  import PhoneManufacturer.TakeSnapshot

  // Manufactured phone counters. Will be incremented, hence var used
  var Phone1Manufactured = 0
  var Phone2Manufactured = 0
  var Phone3Manufactured = 0

  //  delay time for each PhoneManufacturing
  private val manufacturingTime = FiniteDuration(100, MILLISECONDS)

  val actor: ActorParser = Main.yaml.actors.find(c => c.name == "PhoneManufacturer").getOrElse(new ActorParser(null, null))

  case class OrderProcessorPersistence(@BeanProperty var ordersProcessed: Int, @BeanProperty var PhonesManufactured: PhoneManufacturerPersistence)

  //  BeanClass to persist state of PhoneManufacturer actor
  case class PhoneManufacturerPersistence(@BeanProperty var Phone1Manufactured: Int, @BeanProperty var Phone2Manufactured: Int, @BeanProperty var Phone3Manufactured: Int)

  //  CaptureState in snapshot.yaml file
  def captureState(ordersProcessed: Int): Unit = {
    var pm = PhoneManufacturerPersistence(Phone1Manufactured, Phone2Manufactured, Phone3Manufactured)
    var op = OrderProcessorPersistence(ordersProcessed, pm)
    val writer = new FileWriter("Snapshot.yaml")
    val mapper = new ObjectMapper(new YAMLFactory())
    println(op)
    mapper.writeValue(writer, op)
    println("Data persisted in yaml")
    log.info("" + getClass.getName
      + ":\n - " + getClass.getDeclaredField("Phone1Manufactured").toString + " : " + Phone1Manufactured
      + "\n - " + getClass.getDeclaredField("Phone2Manufactured").toString + " : " + Phone2Manufactured
      + "\n - " + getClass.getDeclaredField("Phone3Manufactured").toString + " : " + Phone3Manufactured)
  }

  override def receive: Receive = {
    case TakeSnapshot(ordersProcessed) =>
      println("ordersProcessed:", ordersProcessed)
//      val msg: MessageParser = actor.messages.find(m => m.name == "TakeSnapshot").getOrElse(new MessageParser(null, null))
//      val c = Compiler.compile[String](msg.message.stripMargin)
//      c(Map("ref" -> this, "ordersProcessed" -> ordersProcessed))
        captureState(ordersProcessed)

    // Order for myPhone1 received
    case "Manufacture myPhone1" =>
      val msg: MessageParser = actor.messages.find(m => m.name == "Manufacture myPhone1").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      Phone1Manufactured = c(Map("manufacturingTime" -> manufacturingTime, "Phone1Manufactured" -> Phone1Manufactured, "sender" -> orderProcessor)).toInt

    // Order for myPhone2 received
    case "Manufacture myPhone2" =>
      val msg: MessageParser = actor.messages.find(m => m.name == "Manufacture myPhone2").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      Phone2Manufactured = c(Map("manufacturingTime" -> manufacturingTime, "Phone2Manufactured" -> Phone2Manufactured, "sender" -> orderProcessor)).toInt

    // Order for myPhone3 received
    case "Manufacture myPhone3" =>
      val msg: MessageParser = actor.messages.find(m => m.name == "Manufacture myPhone3").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      Phone3Manufactured = c(Map("manufacturingTime" -> manufacturingTime, "Phone3Manufactured" -> Phone3Manufactured, "sender" -> orderProcessor)).toInt

    // To ignore the order number
    case "Manufacture orderNumber" =>
      val msg: MessageParser = actor.messages.find(m => m.name == "Manufacture orderNumber").getOrElse(new MessageParser(null, null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("log" -> log))
  }
}
