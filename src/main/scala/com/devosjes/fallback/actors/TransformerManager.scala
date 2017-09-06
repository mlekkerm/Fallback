package com.devosjes.fallback.actors

import akka.actor.{Actor, ActorLogging, Props}

object TransformerManager {
  def props(managerId:String):Props = Props(new TransformerManager(managerId))
}

class TransformerManager(managerID:String) extends Actor with ActorLogging {

  val adbsTransformer = context.actorOf(ADSBTransformer.props("adbsTransformer"))

  override def preStart(): Unit = log.info("Transformer manager : " + managerID + " started.")
  override def postStop(): Unit = log.info("Transformer manager : " + managerID + " stopped.")

  override def receive: Receive = Actor.emptyBehavior
}
