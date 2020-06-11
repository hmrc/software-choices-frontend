/*
 * Copyright 2020 HM Revenue & Customs
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

package testOnly.controllers

import config.AppConfig
import javax.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Result}
import testOnly.forms.FeatureSwitchForm
import testOnly.models.FeatureSwitchModel
import uk.gov.hmrc.play.bootstrap.controller.FrontendController

class FeatureSwitchController @Inject()(mcc: MessagesControllerComponents,
                                        implicit val appConfig: AppConfig)
  extends FrontendController(mcc) with I18nSupport {

  def featureSwitch: Action[AnyContent] = Action { implicit request =>
    Ok(testOnly.views.html.featureSwitch(FeatureSwitchForm.form.fill(
      FeatureSwitchModel(
        progressiveDisclosureEnabled = appConfig.features.progressiveDisclosureEnabled(),
        filterViewEnabled = appConfig.features.filterViewEnabled(),
        agentBusinessFilterEnabled = appConfig.features.agentBusinessFilterEnabled(),
        typeOfSoftwareFilterEnabled = appConfig.features.typeOfSoftwareFilterEnabled(),
        additionalSoftwareFilterEnabled = appConfig.features.additionalSoftwareFilterEnabled(),
        priceFilterEnabled = appConfig.features.priceFilterEnabled(),
        accessibilityFilterEnabled = appConfig.features.accessibilityFilterEnabled(),
        providerDetailsEnabled = appConfig.features.providerDetailsEnabled()
      )
    )))
  }

  def submitFeatureSwitch: Action[AnyContent] = Action { implicit request =>
    FeatureSwitchForm.form.bindFromRequest().fold(
      _ => Redirect(routes.FeatureSwitchController.featureSwitch()),
      success = handleSuccess
    )
  }

  def handleSuccess(model: FeatureSwitchModel): Result = {
    appConfig.features.progressiveDisclosureEnabled(model.progressiveDisclosureEnabled)
    appConfig.features.filterViewEnabled(model.filterViewEnabled)
    appConfig.features.agentBusinessFilterEnabled(model.agentBusinessFilterEnabled)
    appConfig.features.additionalSoftwareFilterEnabled(model.additionalSoftwareFilterEnabled)
    appConfig.features.typeOfSoftwareFilterEnabled(model.typeOfSoftwareFilterEnabled)
    appConfig.features.accessibilityFilterEnabled(model.accessibilityFilterEnabled)
    appConfig.features.providerDetailsEnabled(model.providerDetailsEnabled)
    Redirect(controllers.routes.SoftwareChoicesController.show())
  }

}
