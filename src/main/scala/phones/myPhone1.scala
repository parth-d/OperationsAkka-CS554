package phones

import components.body.B1
import components.camera.C1
import components.processor.P1

class myPhone1(newProcessor: P1, newCamera: C1, newBody: B1) {
  val processor: P1 = newProcessor
  val camera: C1 = newCamera
  val body:B1 = newBody
  def get_price(): Int = processor.get_price() + camera.get_price() + body.get_price()
}