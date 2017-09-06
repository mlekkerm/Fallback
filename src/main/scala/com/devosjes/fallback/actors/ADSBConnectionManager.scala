package com.devosjes.fallback.actors

import akka.actor.{Actor, ActorLogging, Props}

object ADSBTransformer {
  def props(transformerId:String):Props = Props(new ADSBTransformer(transformerId))

  final case class ReceiveADSBUpdates(requestID: Long)
  final case class RespondADSBUpdates(requestID: Long, count:Option[Int])
}

class ADSBTransformer(transformerId:String) extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("ADSBTransformer started")
  override def postStop(): Unit = log.info("ADSBTransformer stopped")

  var success: Boolean = false

  override def receive: Receive = Actor.emptyBehavior
}
