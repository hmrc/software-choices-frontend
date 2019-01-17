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

import play.twirl.api.HtmlFormat
import views.html.templates.provider_template

import scala.collection.immutable.Iterable

case class SoftwareChoicesViewModel(providers: Seq[SoftwareProviderModel]){

  val sortedProviders: Map[String, Seq[SoftwareProviderModel]] =
    providers
      .sortBy(f => (f.category, f.name))
      .groupBy(_.category)

  val renderProviders: Iterable[HtmlFormat.Appendable] = sortedProviders.map {
    case (category, providersForCategory) => provider_template(category, providersForCategory)
  }
}
