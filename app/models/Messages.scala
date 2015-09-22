package models

case object JoinLobby
case object LeaveLobby
case class GameMove(i: Int, j: Int)

case class ErrorMessage(s: String)
case class WinMessage(s: String)
case class LoseMessage(s: String)
