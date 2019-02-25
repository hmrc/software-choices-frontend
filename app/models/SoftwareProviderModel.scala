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

import enums.Filter
import enums.Filter._
import play.api.Logger

case class SoftwareProviderModel(name: String, url: String, filters: List[Filter.Value] = List.empty) {

  val category: String = "^[A-Z]".r.findFirstIn(name.toUpperCase).getOrElse("#")
}

object SoftwareProviderModel {

  def apply(fileLine: String): SoftwareProviderModel = {

    Logger.debug(s"[SoftwareChoicesService][readProviders] Provider: $fileLine")

    val fileLineArray = fileLine.split("\\|",-1)
    val filtersArray = fileLineArray.splitAt(2)._2
    val orderedFilters = Seq(BUSINESS, AGENT, ACCOUNTING, SPREADSHEETS, VIEW_RETURN, VIEW_LIABILITIES, VIEW_PAYMENTS, COGNITIVE, VISUAL, HEARING, MOTOR)

    val name = fileLineArray.head
    val url = fileLineArray(1)
    val valid = Seq("X","FULLY")
    val providerFilters = orderedFilters.indices.flatMap(i => if(valid.contains(filtersArray(i).toUpperCase)) Some(orderedFilters(i)) else None).toList

    SoftwareProviderModel(name, url, providerFilters)
  }

}
