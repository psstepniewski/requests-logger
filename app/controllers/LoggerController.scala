package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class LoggerController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with Logging {

  def postRequest(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    logger.info(s"""
      =========================== REQUEST: ${request.method} ${request.uri} ============================
      HEADERS: ${request.headers}
      BODY   : ${request.body}
      =======================================================================================
    """)
    Ok
  }
}
