package controllers

import javax.inject.Inject

import akka.actor._
import models.{Lobby, ClientProxy}
import play.api._
import play.api.libs.json._
import play.api.mvc._
import play.api.Play.current

@Singleton
class Application @Inject()(actorSystem: ActorSystem) extends Controller {

  val lobby = actorSystem.actorOf(Props[Lobby], "lobby")

  def socket = WebSocket.acceptWithActor[JsValue, JsValue] {
    (request: RequestHeader) =>
    (out: ActorRef) =>
    Props(classOf[ClientProxy], lobby, out)
  }

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}








