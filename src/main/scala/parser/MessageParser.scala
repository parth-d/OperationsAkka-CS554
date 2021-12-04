package parser

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Preconditions

//Parses each actor's message
class MessageParser (@JsonProperty("type") _name: String, @JsonProperty("message") _message: String) {
//  To save message name and message behaviour as scala code snippet
  val name = Preconditions.checkNotNull(_name)
  val message = Preconditions.checkNotNull(_message)
}
