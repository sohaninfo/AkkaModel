import akka.actor.{ActorRef, Props, ActorSystem, Actor}

case class Hello()

class HelloActor() extends Actor {
  def receive = {
    case Hello =>
      println("Hello actor!")
  }
}
object HelloApp extends App{
  val system = ActorSystem("Hello-actor")
  val helloActor = system.actorOf(Props[HelloActor], "hello-actor")
  helloActor ! Hello
}
