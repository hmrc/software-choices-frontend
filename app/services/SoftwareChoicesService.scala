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

package services

import enums.Filter
import models.SoftwareProviderModel
import play.api.libs.json._
import utils.LoggingUtil

import java.io.InputStream
import javax.inject.Singleton
import scala.io.Source


@Singleton
class SoftwareChoicesService extends LoggingUtil {

  protected lazy val jsonFile: String = {
    debug("[SoftwareChoicesService][providersList] Loading providers from file")
    val stream: InputStream = getClass.getResourceAsStream("/SoftwareProviders.json")
    Source.fromInputStream(stream).getLines().mkString
  }

  lazy val providersJson: Seq[JsValue] = Json.parse(jsonFile).as[Seq[JsValue]]

  def readProviders(): Seq[SoftwareProviderModel] = providersJson.map(_.as[SoftwareProviderModel])
  lazy val providersList: Seq[SoftwareProviderModel] = readProviders()

  def searchProviders(term: String): Seq[SoftwareProviderModel] = providersList.filter(_.name.toLowerCase.contains(term.toLowerCase))

  def filterProviders(filters: Seq[Filter.Value], term: Option[String] = None): Seq[SoftwareProviderModel] = providersList.filter { providers =>
    filters.forall(providers.filters.contains(_)) && providers.name.toLowerCase.contains(term.getOrElse("").toLowerCase)
  }

  def returnProvider(providerName: String): Option[SoftwareProviderModel] =
    providersList.find(_.name.equals(providerName))

}
