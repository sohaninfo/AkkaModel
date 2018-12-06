import akka.actor.{ActorRef, Props, Actor, ActorSystem}
trait Msg
case class MsgType1() extends Msg

class Printer extends Actor {

  def receive = {
    case MsgType1 => {
      println("Printer: message received")
    }
  }
}

class Mediator extends Actor {
  def receive = {
    case MsgType1 => {
      val printer = context.actorOf(Props[Printer], "printer")
      printer ! MsgType1
    }
  }
}

class Sender extends Actor {
  def receive =  {
    case MsgType1 => {
      val mediator = context.actorOf(Props[Mediator], "mediator")
      println("Sender: sending message to printer")
      mediator ! MsgType1
    }
  }
}

object MediatorApp extends App{
  println("Mediator app")
  val system = ActorSystem("Mediator")
  val sender = system.actorOf(Props[Sender], "sender")
  sender ! MsgType1
  system.terminate()
}


