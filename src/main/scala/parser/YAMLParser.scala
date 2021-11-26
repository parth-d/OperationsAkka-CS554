package parser

import java.util.{List => JList}
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Preconditions
import scala.jdk.CollectionConverters._

class YAMLParser (@JsonProperty("actor") _actor: JList[ActorParser]) {
    val actors: List[ActorParser] = Preconditions.checkNotNull(_actor).asScala.toList
}
