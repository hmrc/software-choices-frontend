/*
 * Copyright 2021 HM Revenue & Customs
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

import java.net.URLEncoder
import java.util.Base64

import config.features.Features
import javax.inject.{Inject, Singleton}
import play.api.i18n.Lang
import play.api.mvc.Call
import uk.gov.hmrc.play.binders.ContinueUrl
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class AppConfig @Inject()(implicit val config: ServicesConfig) {

  private val service: String = "software-choices"

  def languageMap: Map[String, Lang] = Map(
    "english" -> Lang("en"),
    "cymraeg" -> Lang("cy")
  )

  def routeToSwitchLanguage: String => Call = (lang: String) => controllers.routes.LanguageSwitchController.switchToLanguage(lang)

  private val contactHost: String = config.getString(ConfigKeys.contactFrontendService)
  private val contactFormServiceIdentifier: String = "MSCC"

  lazy val reportAProblemPartialUrl: String = s"$contactHost/contact/problem_reports_ajax?service=$contactFormServiceIdentifier"
  lazy val reportAProblemNonJSUrl: String = s"$contactHost/contact/problem_reports_nonjs?service=$contactFormServiceIdentifier"

  lazy val feedbackUrl: String = s"$contactHost/contact/beta-feedback-unauthenticated?service=$contactFormServiceIdentifier" +
    s"&backUrl=${ContinueUrl(host + controllers.routes.SoftwareChoicesController.show().url).encodedUrl}"

  lazy val host: String = config.getString(ConfigKeys.host)

  private def whitelistConfig(key: String): Seq[String] =
    Some(new String(Base64.getDecoder.decode(config.getString(key)), "UTF-8")).map(_.split(",")).getOrElse(Array.empty).toSeq

  lazy val shutterPage: String = config.getString(ConfigKeys.shutterPage)
  lazy val whitelistIps: Seq[String] = whitelistConfig(ConfigKeys.whitelistIps)
  lazy val whitelistExcludedPaths: Seq[Call] = whitelistConfig(ConfigKeys.whitelistExcludedPaths).map(path => Call("GET", path))
  lazy val whiteListEnabled: Boolean = config.getBoolean(ConfigKeys.whitelistEnabled)

  lazy val govUkMtdVatSignUpGuidanceUrl = config.getString(ConfigKeys.govUkMtdVatSignUpGuidance)

  val features = new Features

}
