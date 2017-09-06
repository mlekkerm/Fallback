package com.devosjes.fallback.actors

import akka.actor.{Actor, ActorLogging, Props}

object PersistenceManager {
  def props(persistenceManagerId:String):Props = Props(new PersistenceManager(persistenceManagerId))
}

class PersistenceManager(persistenceManagerId:String) extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("Persistence manager : " + persistenceManagerId + " started.")
  override def postStop(): Unit = log.info("Persistence manager : " + persistenceManagerId + " stopped.")

  override def receive: Receive = Actor.emptyBehavior
}
