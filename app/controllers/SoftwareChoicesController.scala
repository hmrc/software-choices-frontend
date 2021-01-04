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

package controllers

import config.AppConfig
import forms.FiltersForm
import javax.inject.{Inject, Singleton}
import models.SoftwareChoicesFilterViewModel
import play.api.i18n.{I18nSupport, Lang, Messages}
import play.api.mvc.{AnyContent, _}
import services.SoftwareChoicesService
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import views.html.software_choices_filter
import views.html.templates.{provider_info_template, provider_table_template}

@Singleton
class SoftwareChoicesController @Inject()(val softwareChoicesService: SoftwareChoicesService,
                                          val mcc: MessagesControllerComponents
                                         )(implicit val appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport {

  override implicit def request2Messages(implicit request: RequestHeader): Messages =
    if (appConfig.features.welshEnabled()) super.request2Messages else messagesApi.preferred(Seq(Lang("en")))

  val show: Action[AnyContent] = Action { implicit request =>
    Ok(software_choices_filter(softwareProvidersFilterViewModel, FiltersForm.form))
  }

  val search: Action[AnyContent] = Action { implicit request =>
    FiltersForm.form.bindFromRequest().fold(
      error => BadRequest(software_choices_filter(softwareProvidersFilterViewModel, error)),
      search => {
        val results =
          SoftwareChoicesFilterViewModel(
            softwareChoicesService.providersList,
            Some(softwareChoicesService.filterProviders(search.filters, search.searchTerm))
          )
        Ok(software_choices_filter(results, FiltersForm.form.fill(search)))
      }
    )
  }

  lazy val softwareProvidersFilterViewModel = SoftwareChoicesFilterViewModel(
    softwareChoicesService.providersList,
    None
  )

  val ajaxFilterSearch: Action[AnyContent] = Action { implicit request =>
    FiltersForm.form.bindFromRequest().fold(
      error => BadRequest(software_choices_filter(softwareProvidersFilterViewModel, error)),
      search => {
        val results = softwareProvidersFilterViewModel.sortedProviders(softwareChoicesService.filterProviders(search.filters, search.searchTerm))
        val filtered = search.filters.nonEmpty || search.searchTerm.isDefined
        Ok(provider_table_template(
          results,
          softwareChoicesService.providersList.length,
          filtered,
          appConfig.features.providerDetailsEnabled()
        ))
      }
    )
  }

  def ajaxProvider(providerName: String): Action[AnyContent] = Action { implicit request =>
    softwareChoicesService.returnProvider(providerName) match {
      case Some(provider) => Ok(provider_info_template(provider))
      case None => NoContent
    }
  }

}
