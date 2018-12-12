import akka.actor.{Actor, ActorRef, Props, ActorSystem}


object RouteApp extends App{
  val system = ActorSystem("RouteActorSystem")
  val route = system.actorOf(Props[Route], "router")

  route ! Worker.Message("msg1")
  route ! Worker.Message("msg2")
  route ! Worker.Message("msg3")
  route ! Worker.Message("msg4")
  route ! Worker.Message("msg5")
  route ! Worker.Message("msg6")

  Thread.sleep(100)

  system.terminate()
}
