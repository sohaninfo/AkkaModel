import akka.actor.{Actor, ActorRef, Props}

class Route extends Actor{

  val routees:List[ActorRef] = List.fill(5)( context.actorOf( Props[Worker]) )
  override def receive: Receive = {
    case msg:Worker.Message => {
      routees(util.Random.nextInt(routees.size)) forward msg
    }
  }

}
