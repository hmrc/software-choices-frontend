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

package services

import javax.inject.Singleton
import models.SoftwareProviderModel
import play.api.Logger

import scala.io.Source
import enums.Filter
@Singleton
class SoftwareChoicesService {

  protected lazy val providersList: Seq[String] = {
    Logger.debug("[SoftwareChoicesService][providersList] Loading providers from file")
    val stream = getClass.getResourceAsStream("/softwareProviders")
    Source.fromInputStream(stream).getLines.toSeq
  }

  lazy val readProviders: Seq[SoftwareProviderModel] = providersList.map(SoftwareProviderModel(_))

  def searchProviders(term: String): Seq[SoftwareProviderModel] = {
    readProviders.filter(_.name.toLowerCase.contains(term.toLowerCase))
  }

  def filterProviders(filters: List[Filter.Value], term: Option[String] = None): Seq[SoftwareProviderModel] = {

    val filter = readProviders.filter(providers => filters.forall(providers.filters contains))
    term.fold(filter)(searchTerm => filter.filter(_.name.toLowerCase.contains(searchTerm.toLowerCase)))
  }

}
