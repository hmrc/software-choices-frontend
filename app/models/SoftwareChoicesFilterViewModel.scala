/*
 * Copyright 2024 HM Revenue & Customs
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

package models

import config.AppConfig
import play.api.i18n.Messages
import play.twirl.api.HtmlFormat
import views.html.templates.provider_table_template

case class SoftwareChoicesFilterViewModel(allProviders: Seq[SoftwareProviderModel],
                                          filteredProviders: Option[Seq[SoftwareProviderModel]] = None,
                                          view: provider_table_template
                                         )(implicit appConfig: AppConfig) {

  val sortedProviders: Seq[SoftwareProviderModel] => Seq[SoftwareProviderModel] = _.sortBy(_.name.toLowerCase)

  def renderProviders(implicit messages: Messages): HtmlFormat.Appendable =
    view(
      sortedProviders(filteredProviders.getOrElse(allProviders)),
      allProviders.length,
      searchExecuted,
      appConfig.features.providerDetailsEnabled()
    )

  val searchExecuted: Boolean = filteredProviders.isDefined
}
