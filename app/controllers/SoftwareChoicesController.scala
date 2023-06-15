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

import config.AppConfig
import forms.FiltersForm
import models.SoftwareChoicesFilterViewModel
import play.api.i18n.{I18nSupport, Lang, Messages}
import play.api.mvc._
import services.SoftwareChoicesService
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import utils.LoggingUtil
import views.html.templates.{provider_info_template, provider_table_template}
import views.html.{gov_skip_link, software_choices_filter}

import javax.inject.{Inject, Singleton}

@Singleton
class SoftwareChoicesController @Inject()(val softwareChoicesService: SoftwareChoicesService,
                                          val mcc: MessagesControllerComponents,
                                          view: software_choices_filter,
                                          providerView: provider_table_template,
                                          providerInfo: provider_info_template,
                                          gov_skip_link: gov_skip_link
                                         )(implicit val appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport with LoggingUtil {

  override implicit def request2Messages(implicit request: RequestHeader): Messages =
    if (appConfig.features.welshEnabled()) super.request2Messages else messagesApi.preferred(Seq(Lang("en")))

  def show: Action[AnyContent] = Action { implicit request =>
    Ok(view(softwareProvidersFilterViewModel, FiltersForm.form))
  }

   private def titlePrefixMessages(resultCount: Int)(implicit messages: Messages): String = {
      if (resultCount > 0) {
        if (resultCount == 0) {
          messages("common.results.count.oneResult")
        } else {
          messages("common.results.count", resultCount)
        }
      } else {
        messages("common.results.count.notFound")
      }
    }

  def search: Action[AnyContent] = Action { implicit request =>
    FiltersForm.form.bindFromRequest().fold(
      error => {
        warnLog("[SoftwareChoicesController][search] Invalid form")
        BadRequest(view(softwareProvidersFilterViewModel, error))
      },
      search => {
        infoLog("[SoftwareChoicesController][search] Performing search")
        val results =
          SoftwareChoicesFilterViewModel(
            softwareChoicesService.providersList,
            Some(softwareChoicesService.filterProviders(search.filters, search.searchTerm)),
            providerView
          )
        val titlePrefix = titlePrefixMessages(results.filteredProviders.get.length)
        Ok(view(results, FiltersForm.form.fill(search), Some(titlePrefix), skipToResults = Some(gov_skip_link())))
      }
    )
  }

  lazy val softwareProvidersFilterViewModel = SoftwareChoicesFilterViewModel(
    softwareChoicesService.providersList,
    None,
    providerView
  )

  val ajaxFilterSearch: Action[AnyContent] = Action { implicit request =>
    FiltersForm.form.bindFromRequest().fold(
      error => BadRequest(view(softwareProvidersFilterViewModel, error)),
      search => {
        val results = softwareProvidersFilterViewModel.sortedProviders(softwareChoicesService.filterProviders(search.filters, search.searchTerm))
        val filtered = search.filters.nonEmpty || search.searchTerm.isDefined
        Ok(providerView(
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
      case Some(provider) => Ok(providerInfo(provider))
      case None => NoContent
    }
  }

}
