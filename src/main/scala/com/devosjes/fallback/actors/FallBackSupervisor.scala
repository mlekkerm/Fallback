package com.devosjes.fallback.actors

import akka.actor.{Actor, ActorLogging, Props}

object FallBackSupervisor {
  def props():Props = Props(new FallBackSupervisor)
}

class FallBackSupervisor extends Actor with ActorLogging {

  val transformerManager = context.actorOf(TransformerManager.props("Transformer Manager"))
  val persistenceManager = context.actorOf(PersistenceManager.props("Persistence Manager"))

  override def preStart(): Unit = log.info("FallBack Application started")
  override def postStop(): Unit = log.info("FallBack Application stopped")

  override def receive: Receive = Actor.emptyBehavior
}

