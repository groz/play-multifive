package models

import akka.actor.{Actor, ActorRef}
import play.api.libs.json._

class ClientProxy(lobby: ActorRef, out: ActorRef) extends Actor {

  lobby ! JoinLobby

  override def postStop = lobby ! LeaveLobby

  override def receive: Actor.Receive = {

    case GameStarted(fieldSize, isFirst) =>
      out ! JsObject(Map(
        "commandType" -> JsString("Start"),
        "fieldSize" -> JsNumber(fieldSize),
        "isFirst" -> JsBoolean(isFirst)
      ))

      context become process(sender)
  }

  def process(game: ActorRef): Actor.Receive = {

    case msg: JsValue =>
      val commandType = (msg \ "commandType").as[String]
      val row = (msg \ "row").as[Int]
      val column = (msg \ "column").as[Int]
      game ! GameMove(row, column)

    case GameMove(i, j) =>
      out ! JsObject(Map(
        "commandType" -> JsString("Move"),
        "row" -> JsNumber(i),
        "column" -> JsNumber(j)
      ))

    case msg =>
      println(s"Unrecognized message $msg")
  }
}
