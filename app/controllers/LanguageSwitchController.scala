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

package controllers

import controllers.LanguageSwitchController.{english, welsh}
import play.api.Logging
import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc.{Action, AnyContent, Flash, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject


class LanguageSwitchController @Inject()(cc: MessagesControllerComponents)
  extends FrontendController(cc)
    with I18nSupport
    with Logging {

  def switchToEnglish: Action[AnyContent] = switchToLang(english)

  def switchToWelsh: Action[AnyContent] = switchToLang(welsh)

  private def switchToLang(lang: Lang): Action[AnyContent] = Action { implicit request =>
    request.headers.get(REFERER) match {
      case Some(referrer) => Redirect(referrer).withLang(lang).flashing(LanguageSwitchController.FlashWithSwitchIndicator)
      case None =>
        logger.warn("Unable to get the referrer, so sending them to the start.")
        Redirect(routes.SoftwareChoicesController.show).withLang(lang)
    }
  }

}

object LanguageSwitchController {
  private val SwitchIndicatorKey: String      = "switching-language"
  private val FlashWithSwitchIndicator: Flash = Flash(Map(SwitchIndicatorKey -> "true"))
  val english: Lang                           = Lang("en")
  val welsh: Lang                             = Lang("cy")
}
