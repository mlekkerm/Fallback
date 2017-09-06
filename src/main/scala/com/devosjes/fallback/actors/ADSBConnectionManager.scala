package com.devosjes.fallback.actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.ByteString

object AirplaneTransformer {
  def props(transformerId:String):Props = Props(new AirplaneTransformer(transformerId))

  final case class ReceiveADSBUpdates(requestID: Long)
  final case class RespondADSBUpdates(requestID: Long, count:Option[Int])
}

class AirplaneTransformer(transformerId:String) extends Actor with ActorLogging {

  import akka.pattern.pipe
  import context.dispatcher

  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
  val http = Http(context.system)

  override def preStart(): Unit = {
    http.singleRequest(HttpRequest(uri="https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json"))
      .pipeTo(self)
    log.info("ADSBTransformer started")
  }

  def receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach( { body =>
        log.info("Got response, body: " + body.utf8String)
      })
    case resp @ HttpResponse(code,_,_,_) =>
      log.info("Request failed, response code: " + code)
      resp.discardEntityBytes()
  }

  override def postStop(): Unit = log.info("ADSBTransformer stopped")
}
