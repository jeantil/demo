package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json.{JsError, JsValue, Json}
import slug.BuildInfo
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
case class Credentials(name:String, password:String)
object Credentials {
  implicit val credentialsFormat = Json.format[Credentials]
}
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.obj("version"->BuildInfo.version))
  }
  def foo() = Action(parse.json) { implicit request: Request[JsValue] =>
    val credentialJSR= Json.fromJson[Credentials](request.body)
    credentialJSR.fold(
      errors => BadRequest(JsError.toJson(errors)),
      credentials => Ok(Json.obj("version"->BuildInfo.version, "echo"->Json.toJson(credentials)))

    )
  }

}

