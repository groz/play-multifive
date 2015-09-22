package models

import akka.actor.{ActorRef, Actor}

class Game(p1: ActorRef, p2: ActorRef) extends Actor {

  val FIELD_SIZE = 20

  val field = Array.fill(FIELD_SIZE, FIELD_SIZE)(0)

  override def receive: Receive = process(p1)

  def checkIfWin(field: Array[Array[Int]], v: Int): Boolean = {
    field.map(a => a.count(_ == v)).sum > 7
  }


  def process(turnOwner: ActorRef): Receive = {

    case GameMove(i,j) =>
      if (field(i)(j) != 0) {
        // make move
        val v = if (turnOwner == p1) 1 else 2
        field(i)(j) = v
        // check if win
        if (checkIfWin(field, v)) {
          turnOwner ! WinMessage("Win!")
          (Set(p1,p2) - turnOwner).head ! LoseMessage("Lose!")
        }
        else
          context become process(if (v==1) p2 else p1)
      }
      else {
        // abort
        turnOwner ! ErrorMessage("Wrong move")
      }
  }
}
