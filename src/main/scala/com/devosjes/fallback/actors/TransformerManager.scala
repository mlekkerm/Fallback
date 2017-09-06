package com.devosjes.fallback.actors

import akka.actor.{Actor, ActorLogging, Props}
import com.devosjes.fallback.actors.AirplaneTransformer.StartRetrievingUpdates

object TransformerManager {
  def props(managerId:String):Props = Props(new TransformerManager(managerId))
}

class TransformerManager(managerID:String) extends Actor with ActorLogging {

  val airplaneTransformer = context.actorOf(AirplaneTransformer.props("airplaneTransformer"))

  override def preStart(): Unit = log.info("Transformer manager : " + managerID + " started.")
  override def postStop(): Unit = log.info("Transformer manager : " + managerID + " stopped.")

  override def receive: Receive = Actor.emptyBehavior

  airplaneTransformer ! StartRetrievingUpdates(1)
}
