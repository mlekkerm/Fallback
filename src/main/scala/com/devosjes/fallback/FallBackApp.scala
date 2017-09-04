package com.devosjes.fallback

import akka.actor.ActorSystem
import com.devosjes.fallback.actors.FallBackSupervisor

import scala.io.StdIn

object FallBackApp {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("fallback-system")

    try {
      // create top level supervisor
      val supervisor = system.actorOf(FallBackSupervisor.props())

      // Exit the system after ENTER is pressed

      StdIn.readLine()
    } finally {
      system.terminate()
    }
  }
}
