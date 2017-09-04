package com.devosjes.fallback.actors

import akka.actor.{Actor, ActorLogging, Props}

object ADSBConnectionManager {
  def props():Props = Props(new ADSBConnectionManager)

  final case class ReceiveADSBUpdates(requestID: Long)
  final case class RespondADSBUpdates(requestID: Long, count:Option[Int])
}

class ADSBConnectionManager extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("ADSBConnectionManager started")
  override def postStop(): Unit = log.info("ADSBConnectionManager stopped")

  var success: Boolean = false

  override def receive: Receive = Actor.emptyBehavior
}
