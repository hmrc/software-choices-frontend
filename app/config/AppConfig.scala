/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package config

import java.util.Base64

import javax.inject.{Inject, Singleton}
import play.api.{Configuration, Environment}
import play.api.Mode.Mode
import uk.gov.hmrc.play.binders.ContinueUrl
import play.api.mvc.Call
import uk.gov.hmrc.play.config.ServicesConfig

@Singleton
class AppConfig @Inject()(val runModeConfiguration: Configuration, environment: Environment) extends ServicesConfig {
  override protected def mode: Mode = environment.mode

  private val contactHost: String = getString(ConfigKeys.contactFrontendService)
  private val contactFormServiceIdentifier: String = "MSCC"

  lazy val analyticsToken: String = getString(ConfigKeys.googleAnalyticsToken)
  lazy val analyticsHost: String = getString(ConfigKeys.googleAnalyticsToken)

  lazy val reportAProblemPartialUrl: String = s"$contactHost/contact/problem_reports_ajax?service=$contactFormServiceIdentifier"
  lazy val reportAProblemNonJSUrl: String = s"$contactHost/contact/problem_reports_nonjs?service=$contactFormServiceIdentifier"

  lazy val feedbackUrl: String = s"$contactHost/contact/beta-feedback-unauthenticated?service=$contactFormServiceIdentifier" +
    s"&backUrl=${ContinueUrl(host + controllers.routes.HelloWorld.helloWorld().url).encodedUrl}"

  lazy val host: String = getString(ConfigKeys.host)

  private def whitelistConfig(key: String): Seq[String] =
    Some(new String(Base64.getDecoder.decode(runModeConfiguration.getString(key)
      .getOrElse("")), "UTF-8")).map(_.split(",")).getOrElse(Array.empty).toSeq

  lazy val shutterPage: String = getString(ConfigKeys.shutterPage)
  lazy val whitelistIps: Seq[String] = whitelistConfig(ConfigKeys.whitelistIps)
  lazy val whitelistExcludedPaths: Seq[Call] = whitelistConfig(ConfigKeys.whitelistExcludedPaths).map(path => Call("GET", path))
  lazy val whiteListEnabled: Boolean = getBoolean(ConfigKeys.whitelistEnabled)

}
