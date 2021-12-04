package parser

import java.util.{List => JList}
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Preconditions
import scala.jdk.CollectionConverters._

//Parses input.yaml into Actor list
class YAMLParser (@JsonProperty("actor") _actor: JList[ActorParser]) {
//    To save list of actors
    val actors: List[ActorParser] = Preconditions.checkNotNull(_actor).asScala.toList
}
