import Filter.FilterRequest
import Handler.HandlerRequest
import Processor.ProcessorRequest
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class Message(msg:String)

object Processor {
  trait ProcessorMessage

  case class ProcessorRequest(msg:Message) extends ProcessorMessage

  def props(filter:ActorRef) = Props(new Processor(filter))
}

class Processor(filter:ActorRef) extends Actor {
  override def receive: Receive = {
    case ProcessorRequest(msg) => {
      filter ! FilterRequest(msg)
    }
  }
}

object Filter {
  trait FilterMessage
  case class FilterRequest(msg: Message) extends FilterMessage
  def props(handler: ActorRef) = Props(new Filter(handler))
}
class Filter(handler: ActorRef) extends Actor{
  val badMessages = List(Message("bad"))
  override def receive: Receive = {
    case FilterRequest(msg) if badMessages.contains(msg) => {
     println("Rejecting bad message")
    }
    case FilterRequest(msg) => {
      println("Forwarding good message to handler")
      handler ! HandlerRequest(msg)
    }
  }
}

object Handler {
  trait HandlerMessage
  case class HandlerRequest(msg:Message) extends HandlerMessage
}

class Handler() extends  Actor{

  override def receive: Receive = {
    case HandlerRequest(msg) => {
      println(s"Message received:$msg")
    }
  }
}

object FilterActorApp extends App{

  val system = ActorSystem("FilterActor")
  val handler = system.actorOf(Props[Handler], "handler")
  val filter = system.actorOf(Filter.props(handler), "filter")

  val processor = system.actorOf(Processor.props(filter), "processor")

  processor ! ProcessorRequest(Message("good"))
  processor ! ProcessorRequest(Message("bad"))
  processor ! ProcessorRequest(Message("good"))

  Thread.sleep(100)

  system.terminate()

}