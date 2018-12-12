import Worker.Message
import akka.actor.{Actor, ActorRef}

object Worker {
  sealed trait WorkerMsg
  case class Message(msg: String) extends WorkerMsg
}

class Worker extends Actor {
  override def receive: Receive = {
    case Message(msg) => {
      println(s"Worker: $self :message received: $msg")
    }
  }
}
