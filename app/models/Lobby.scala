package models

import akka.actor.Actor

class Lobby extends Actor {

  override def receive: Receive = {

    case msg =>
      println(s"Lobby couldn't handle message $msg")

  }


}
