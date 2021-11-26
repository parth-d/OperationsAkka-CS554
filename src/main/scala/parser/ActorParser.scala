package parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Preconditions
import java.util.{List => JList}
import scala.jdk.CollectionConverters._

//Parses each Actor in yaml file
class ActorParser(@JsonProperty("name") _name: String, @JsonProperty("messages") _messages: JList[MessageParser]) {
  val name = Preconditions.checkNotNull(_name)
  val messages: List[MessageParser] = Preconditions.checkNotNull(_messages).asScala.toList
}
