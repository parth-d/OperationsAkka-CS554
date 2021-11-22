package actors

import actors.OrderProcessor.TakeSnapshot
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import components.body.{B1, B2, B3}
import components.camera.C1
import components.processor.P1
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import phones.{myPhone1, myPhone2, myPhone3}

import java.io.FileWriter
import scala.beans.BeanProperty
import scala.concurrent.duration.{Duration, FiniteDuration, MILLISECONDS}

class PhoneManufacturer extends Actor with ActorLogging{
  var Phone1Manufactured = 0
  var Phone2Manufactured = 0
  var Phone3Manufactured = 0
  private val manufacturingTime = FiniteDuration(Duration("1 seconds").toMillis, MILLISECONDS)

  case class PhoneManufacturerPersistence(@BeanProperty var Phone1Manufactured: Int, @BeanProperty var Phone2Manufactured: Int, @BeanProperty var Phone3Manufactured: Int)

  def captureState()= {
    val pm = PhoneManufacturerPersistence(Phone1Manufactured, Phone2Manufactured, Phone3Manufactured)
    val writer = new FileWriter("sample.yaml")
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
    case TakeSnapshot => captureState()
    case "Manufacture myPhone1" =>
//      println("Received order to process myPhone1")
      val loopTil = System.nanoTime()+manufacturingTime.toNanos
      while (System.nanoTime() < loopTil) {
      }
      Phone1Manufactured += 1
      sender() ! new myPhone1(new P1(), new C1, new B1)

    case "Manufacture myPhone2" =>
//      println("Received order to process myPhone2")
      val loopTil = System.nanoTime()+manufacturingTime.toNanos
      while (System.nanoTime() < loopTil) {
      }
      Phone2Manufactured += 1
      sender() ! new myPhone2(new P1(), new C1, new B2)

    case "Manufacture myPhone3" =>
//      println("Received order to process myPhone3")
      val loopTil = System.nanoTime()+manufacturingTime.toNanos
      while (System.nanoTime() < loopTil) {
      }
      Phone3Manufactured += 1
      sender() ! new myPhone3(new P1(), new C1, new B3)

  }
}
