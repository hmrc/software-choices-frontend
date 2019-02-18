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

import play.api.Logger
import enums.Filter

case class SoftwareProviderModel(name: String, url: String, filters: List[Filter.Value] = List.empty) {

  val category: String = "^[A-Z]".r.findFirstIn(name.toUpperCase).getOrElse("#")
}

object SoftwareProviderModel {

  def apply(fileLine: String): SoftwareProviderModel = {

    Logger.debug(s"[SoftwareChoicesService][readProviders] Provider: $fileLine")

    val splitAtAsterisk = fileLine.split('*')

    val nameUrl = splitAtAsterisk.head.split('|')

    val filters: List[Filter.Value] = splitAtAsterisk.last.split('|').map(Filter(_)).toList

    new SoftwareProviderModel(nameUrl(0), nameUrl(1), filters)
  }

}
