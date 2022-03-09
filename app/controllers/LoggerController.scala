package controllers

import play.api._
import play.api.mvc._

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject._

@Singleton
class LoggerController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with Logging {

  def postResponses(httpCode: Int, rejectBefore: Option[String], responseBody: Option[String]): Action[AnyContent] = logRequest(httpCode, rejectBefore, responseBody)
  def getResponses(httpCode: Int, rejectBefore: Option[String], responseBody: Option[String]): Action[AnyContent] = logRequest(httpCode, rejectBefore, responseBody)

  private def logRequest(httpCode: Int, rejectBefore: Option[String], responseBody: Option[String]): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>

    def logRequest(httpCode: Int, responseBody: Option[String]): Unit =
      logger.info(s"""
        =========================== REQUEST: ${request.method} ${request.uri} ============================
        QUERY   : ${request.queryString}
        HEADERS : ${request.headers}
        BODY    : ${request.body}
        RESPONSE: $httpCode BODY(${responseBody.getOrElse("")})
        =======================================================================================
      """)

    def badRequest(): Result = {
      logRequest(400, None)
      BadRequest
    }

    def result(httpCode: Int, responseBody: Option[String]) = {
      responseBody match {
        case Some(body) =>
          logRequest(httpCode, Some(body))
          Results.Status(httpCode).apply(body)
        case None =>
          logRequest(httpCode, None)
          Results.Status(httpCode)
      }
    }


    rejectBefore match {
      case Some(before) =>
        if(OffsetDateTime.now().isBefore(OffsetDateTime.parse(before, DateTimeFormatter.ISO_OFFSET_DATE_TIME))) badRequest()
        else result(httpCode, responseBody)
      case None =>
        result(httpCode, responseBody)
    }
  }
}
