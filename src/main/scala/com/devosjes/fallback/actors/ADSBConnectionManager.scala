package com.devosjes.fallback.actors

import akka.actor.{Actor, ActorLogging, Props, Timers}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.ByteString
import scala.concurrent.duration._

object AirplaneTransformer {
  def props(transformerId:String):Props = Props(new AirplaneTransformer(transformerId))

  final case class StartRetrievingUpdates(requestID: Long)
  final case class StopRetrievingUpdates(requestID: Long, count:Option[Int])

  // internal messages used for timer
  private case object TickKey
  private case object FirstTick
  private case object Tick
}

class AirplaneTransformer(transformerId:String) extends Actor with ActorLogging with Timers {

  import AirplaneTransformer._
  import akka.pattern.pipe
  import context.dispatcher

  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
  val http = Http(context.system)
  val url="https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json?lat=33.433638&lng=-112.008113&fDstL=0&fDstU=100"

  override def preStart(): Unit = {

    log.info(transformerId + " started")
  }

  def receive = {
    case StartRetrievingUpdates(_) =>
      if(! timers.isTimerActive(TickKey))
        timers.startSingleTimer(TickKey, FirstTick, 500.millis )

    case FirstTick =>
      http.singleRequest(HttpRequest(uri=url))
        .pipeTo(self)

      // repeat request every 30 seconds
      timers.startPeriodicTimer(TickKey, Tick, 30.second)

    case Tick =>
      // retrieve new data
      http.singleRequest(HttpRequest(uri=url))
        .pipeTo(self)

    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach( { body =>
        log.info("Got response, body: " + body.utf8String)
      })
    case resp @ HttpResponse(code,_,_,_) =>
      log.info("Request failed, response code: " + code)
      resp.discardEntityBytes()
  }

  override def postStop(): Unit = log.info(transformerId + " stopped")
}
