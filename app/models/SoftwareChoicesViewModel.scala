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
import views.html.templates.{found_provider_template, provider_template}

case class SoftwareChoicesViewModel(allProviders: Seq[SoftwareProviderModel], foundProviders: Seq[SoftwareProviderModel] = Seq.empty){

  type GroupedProviders = Seq[(String, Seq[SoftwareProviderModel])]

  private val sortedProviders: Seq[SoftwareProviderModel] => Seq[SoftwareProviderModel] = _.sortBy(_.name.toLowerCase())

  private val groupByCategory: Seq[SoftwareProviderModel] => GroupedProviders = _.groupBy(_.category).toSeq.sortBy(_._1)

  def renderAllProviders(implicit messages: Messages): Seq[HtmlFormat.Appendable] = groupByCategory(sortedProviders(allProviders)).map {
    case (category, providersForCategory) => provider_template(category, providersForCategory)
  }

  def renderFoundProviders(implicit messages: Messages): HtmlFormat.Appendable = found_provider_template(sortedProviders(foundProviders))
}
