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

package models

import play.api.i18n.Messages
import play.twirl.api.HtmlFormat
import views.html.templates.{provider_table_template, result_count_template}

case class SoftwareChoicesFilterViewModel(allProviders: Seq[SoftwareProviderModel],
                                          filteredProviders: Option[Seq[SoftwareProviderModel]] = None) {

  private val sortedProviders: Seq[SoftwareProviderModel] => Seq[SoftwareProviderModel] = _.sortBy(_.name.toLowerCase)

  def renderProviders(implicit messages: Messages): HtmlFormat.Appendable = {
    val results: Seq[SoftwareProviderModel] = filteredProviders.fold(allProviders) {
      case filterResults if filterResults.nonEmpty => filterResults
      case _ => allProviders
    }
    provider_table_template(sortedProviders(results))
  }

  def renderResultCount(implicit messages: Messages): HtmlFormat.Appendable = {
    result_count_template(filteredProviders.fold(allProviders.length)(_.length), allProviders.length)
  }
}
