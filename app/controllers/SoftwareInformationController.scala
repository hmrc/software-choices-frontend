/*
 * Copyright 2022 HM Revenue & Customs
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

import config.AppConfig

import javax.inject.{Inject, Singleton}
import play.api.i18n.I18nSupport
import play.api.mvc.{AnyContent, _}
import services.SoftwareChoicesService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import views.html.software_choices_software_information

@Singleton
class SoftwareInformationController @Inject()(val mcc: MessagesControllerComponents,
                                              val softwareChoicesService: SoftwareChoicesService,
                                              view: software_choices_software_information
                                             )(implicit val appConfig: AppConfig)
  extends FrontendController(mcc) with I18nSupport {

  def show(providerName: String): Action[AnyContent] = Action { implicit request =>
    softwareChoicesService.returnProvider(providerName) match {
      case Some(provider) => Ok(view(provider))
      case None => NoContent
    }
  }

}
