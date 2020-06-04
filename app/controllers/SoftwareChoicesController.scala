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

package controllers

import config.AppConfig
import forms.{FiltersForm, SearchForm}
import javax.inject.{Inject, Singleton}
import models.SoftwareChoicesViewModel.sortProviders
import models.{SoftwareChoicesFilterViewModel, SoftwareChoicesViewModel}
import play.api.i18n.I18nSupport
import play.api.mvc.{AnyContent, _}
import services.SoftwareChoicesService
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import views.html.templates.provider_table_template
import views.html.{software_choices_filter, software_choices_results, software_choices_search}

@Singleton
class SoftwareChoicesController @Inject()(val softwareChoicesService: SoftwareChoicesService,
                                          val mcc: MessagesControllerComponents
                                         )(implicit val appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport {

  //Feature Switch Routing Logic
  val show: Action[AnyContent] = Action { implicit request =>
    if(appConfig.features.filterViewEnabled()) filterView else basicSearchView
  }

  val search: Action[AnyContent] = Action { implicit request =>
    if(appConfig.features.filterViewEnabled()) filterSearch else basicSearchSubmit
  }

  //Basic Search View Logic - Production MVP
  lazy val softwareProviders = SoftwareChoicesViewModel(softwareChoicesService.providersList)

  def basicSearchView(implicit request: Request[_]): Result =
    Ok(software_choices_search(softwareProviders, SearchForm.form))

  def basicSearchSubmit(implicit request: Request[_]): Result =
    SearchForm.form.bindFromRequest().fold(
      error => BadRequest(software_choices_search(softwareProviders, error)),
      search => {
        val results = SoftwareChoicesViewModel(softwareChoicesService.providersList, softwareChoicesService.searchProviders(search.term))
        Ok(software_choices_results(results, SearchForm.form.fill(search)))
      }
    )


  //Filter View Logic
  lazy val softwareProvidersFilterViewModel = SoftwareChoicesFilterViewModel(softwareChoicesService.providersList)

  def filterView(implicit request: Request[_]): Result = Ok(software_choices_filter(softwareProvidersFilterViewModel, FiltersForm.form))

  def filterSearch(implicit request: Request[_]): Result =
    FiltersForm.form.bindFromRequest().fold(
      error => BadRequest(software_choices_filter(softwareProvidersFilterViewModel, error)),
      search => {
        val results =
          SoftwareChoicesFilterViewModel(softwareChoicesService.providersList, Some(softwareChoicesService.filterProviders(search.filters, search.searchTerm)))
        Ok(software_choices_filter(results, FiltersForm.form.fill(search)))
      }
    )

  val ajaxFilterSearch: Action[AnyContent] = Action { implicit request =>
    FiltersForm.form.bindFromRequest().fold(
      error => BadRequest(software_choices_filter(softwareProvidersFilterViewModel, error)),
      search => {
        val results = sortProviders(softwareChoicesService.filterProviders(search.filters, search.searchTerm))
        val filtered = search.filters.nonEmpty || search.searchTerm.isDefined
        Ok(provider_table_template(results, softwareChoicesService.providersList.length, filtered))
      }
    )
  }

  def ajaxProviderJson(providerName: String): Action[AnyContent] = Action { implicit request =>
    softwareChoicesService.returnProviderJson(providerName) match {
      case Some(providerJson) => Ok(providerJson)
      case None => NoContent
    }
  }

}
