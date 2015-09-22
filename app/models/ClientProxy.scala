package models

import akka.actor.{Actor, ActorRef}
import play.api.libs.json.{JsObject, JsValue}

class ClientProxy(lobby: ActorRef, out: ActorRef) extends Actor {

  lobby ! JoinLobby

  override def postStop = lobby ! LeaveLobby

  override def receive: Actor.Receive = {

    case msg: JsValue =>
      out ! JsObject(Map("ok" -> msg))

    case msg =>
      println(s"Unrecognized message $msg")
  }
}
