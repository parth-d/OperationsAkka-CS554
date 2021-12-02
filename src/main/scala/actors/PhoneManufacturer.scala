package actors

import actors.OrderProcessor.TakeSnapshot
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import parser.{ActorParser, MessageParser}
import java.io.FileWriter
import scala.beans.BeanProperty
import scala.concurrent.duration.{Duration, FiniteDuration, MILLISECONDS}

object  PhoneManufacturer{
  def props(orderProcessor: ActorRef): Props = Props(new PhoneManufacturer(orderProcessor))
}

class PhoneManufacturer(orderProcessor: ActorRef) extends Actor with ActorLogging{

  var Phone1Manufactured = 0
  var Phone2Manufactured = 0
  var Phone3Manufactured = 0

  val actor : ActorParser =Main.yaml.actors.find(c => c.name=="PhoneManufacturer" ).getOrElse(new ActorParser(null,null))

  //  delay time for each PhoneManufacturing
  private val manufacturingTime = FiniteDuration(Duration("1 seconds").toMillis, MILLISECONDS)

//  BeanClass to persist state of PhoneManufacturer actor
  case class PhoneManufacturerPersistence(@BeanProperty var Phone1Manufactured: Int, @BeanProperty var Phone2Manufactured: Int, @BeanProperty var Phone3Manufactured: Int)

//  CaptureState in snapshot.yaml file
   def captureState(): Unit = {
      val pm = PhoneManufacturerPersistence(Phone1Manufactured, Phone2Manufactured, Phone3Manufactured)
      val writer = new FileWriter("Snapshot.yaml")
      val mapper = new ObjectMapper(new YAMLFactory())
      println(pm)
      mapper.writeValue(writer, pm)
      println("Data persisted in yaml")
      log.info(""+getClass.getName
        +":\n - "+getClass.getDeclaredField("Phone1Manufactured").toString+" : "+Phone1Manufactured
        +"\n - "+getClass.getDeclaredField("Phone2Manufactured").toString+" : "+Phone2Manufactured
        +"\n - "+getClass.getDeclaredField("Phone3Manufactured").toString+" : "+Phone3Manufactured)
    }

  override def receive: Receive = {
    case TakeSnapshot =>
      val msg: MessageParser = actor.messages.find(m=> m.name == "TakeSnapshot").getOrElse(new MessageParser(null,null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("ref"->this))
    case "Manufacture myPhone1" =>
      val msg: MessageParser = actor.messages.find(m=> m.name == "Manufacture myPhone1").getOrElse(new MessageParser(null,null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      Phone1Manufactured = c(Map("manufacturingTime"->manufacturingTime, "Phone1Manufactured"-> Phone1Manufactured, "sender"->orderProcessor)).toInt
    case "Manufacture myPhone2" =>
      val msg: MessageParser = actor.messages.find(m=> m.name == "Manufacture myPhone2").getOrElse(new MessageParser(null,null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      Phone2Manufactured = c(Map("manufacturingTime"->manufacturingTime, "Phone2Manufactured"-> Phone2Manufactured, "sender"->orderProcessor)).toInt
    case "Manufacture myPhone3" =>
      val msg: MessageParser = actor.messages.find(m=> m.name == "Manufacture myPhone3").getOrElse(new MessageParser(null,null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      Phone3Manufactured = c(Map("manufacturingTime"->manufacturingTime, "Phone3Manufactured"-> Phone3Manufactured, "sender"->orderProcessor)).toInt
    case "Manufacture orderNumber" =>
      val msg: MessageParser = actor.messages.find(m=> m.name == "Manufacture orderNumber").getOrElse(new MessageParser(null,null))
      val c = Compiler.compile[String](msg.message.stripMargin)
      c(Map("log"->log))
  }
}
