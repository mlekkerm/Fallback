import akka.actor.{Actor, ActorSystem, Props}

class FallbackSupervisor extends Actor {
  override def preStart():Unit = {
    println("actor: " + self + " started")

    // create supervised actors
  }

  override def postStop():Unit = {
    println("actor: " + self + " stopped")
  }

  override def receive: Receive = {
    case "start" => println("Actor: " + self + " already started...")
    case "stop" => context.stop(self)
  }
}

object CombinedTest extends App {

  val system = ActorSystem()

  val fallbackSupervisor = system.actorOf(Props[FallbackSupervisor],"FallbackSupervisor")

  fallbackSupervisor ! "start"
  fallbackSupervisor ! "stop"

}

