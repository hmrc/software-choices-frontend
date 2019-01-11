package uk.gov.hmrc.softwarechoicesfrontend.config

import akka.stream.Materializer
import javax.inject.Inject
import play.api.mvc.{Call, RequestHeader, Result}
import uk.gov.hmrc.whitelist.AkamaiWhitelistFilter

import scala.concurrent.Future


class WhitelistFilter @Inject()(val appConfig: AppConfig, implicit val mat: Materializer) extends AkamaiWhitelistFilter {

  override val whitelist: Seq[String] = appConfig.whitelistIps
  override val destination: Call = Call("GET", appConfig.shutterPage)
  override val excludedPaths: Seq[Call] = appConfig.whitelistExcludedPaths

  override def apply(f: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = {
    if(appConfig.whiteListEnabled) {
      super.apply(f)(rh)
    } else {
      f(rh)
    }
  }
}