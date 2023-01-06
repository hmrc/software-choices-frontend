/*
 * Copyright 2023 HM Revenue & Customs
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

import config.features.Features
import play.api.i18n.Lang
import play.api.mvc.Call
import uk.gov.hmrc.play.bootstrap.binders.RedirectUrl
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject()(implicit val config: ServicesConfig) {

  def languageMap: Map[String, Lang] = Map(
    "english" -> Lang("en"),
    "cymraeg" -> Lang("cy")
  )

  def routeToSwitchLanguage: String => Call = (lang: String) => controllers.routes.LanguageSwitchController.switchToLanguage(lang)

  private val contactHost: String = config.getString(ConfigKeys.contactFrontendService)
  private val contactFormServiceIdentifier: String = "MSCC"

  lazy val feedbackUrl: String = s"$contactHost/contact/beta-feedback-unauthenticated?service=$contactFormServiceIdentifier" +
    s"&backUrl=${RedirectUrl(host + controllers.routes.SoftwareChoicesController.show.url)}"

  lazy val host: String = config.getString(ConfigKeys.host)

  lazy val govUkMtdVatSignUpGuidanceUrl = config.getString(ConfigKeys.govUkMtdVatSignUpGuidance)

  val features = new Features

}
