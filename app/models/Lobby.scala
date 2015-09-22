package models

import akka.actor.{Props, ActorRef, Actor}

class Lobby extends Actor {

  override def receive: Receive = process(Set.empty)

  def process(actors: Set[ActorRef]): Receive = {
    case JoinLobby =>
      if (actors.size > 0) {
        context.actorOf(Props(classOf[Game], actors.head, sender))
        context become process(Set.empty)
      }
      else
        context become process(actors + sender)


    case LeaveLobby =>
      context become process(actors - sender)

  }


}
