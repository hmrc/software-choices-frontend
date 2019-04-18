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

import enums.Filter
import javax.inject.Singleton
import models.SoftwareProviderModel
import play.api.Logger
import play.api.libs.json._

import scala.io.Source


@Singleton
class SoftwareChoicesService {

  protected lazy val providersList: Seq[String] = {
    Logger.debug("[SoftwareChoicesService][providersList] Loading providers from file")
    val stream = getClass.getResourceAsStream("/softwareProvidersCSV")
    Source.fromInputStream(stream).getLines.toSeq
  }

  lazy val readJson: Seq[SoftwareProviderModel] = Json.parse(json).as[Seq[JsValue]] map(_.as[SoftwareProviderModel])

  def readProviders: Seq[SoftwareProviderModel] = providersList.map(SoftwareProviderModel(_))

  lazy val providers: Seq[SoftwareProviderModel] = readProviders

  def searchProviders(term: String): Seq[SoftwareProviderModel] = providers.filter(_.name.toLowerCase.contains(term.toLowerCase))

  def filterProviders(filters: Seq[Filter.Value], term: Option[String] = None): Seq[SoftwareProviderModel] = providers.filter { providers =>
    filters.forall(providers.filters.contains(_)) && providers.name.toLowerCase.contains(term.getOrElse("").toLowerCase)
  }


  val json =
    """
      |[
      | {
      |   "name": "@DataDear Excel Add-in",
      |   "url": "https://www.datadear.com/hmrc-vat-return-making-tax-digital/",
      |   "businesses": "true",
      |   "agents": "true",
      |   "digitalRecordKeeping": "true",
      |   "bridgingSoftware": "true",
      |   "viewVatReturn": "true",
      |   "viewVatLiabilities": "true",
      |   "viewVatPayments": "true",
      |   "cognitive": "false",
      |   "visual": "false",
      |   "hearing": "false",
      |   "motor": "false"
      | },
      | {
      |   "name": "@gosimpletax",
      |   "url": "https://books.gosimplesoftware.co.uk/",
      |   "businesses": "true",
      |   "agents": "true",
      |   "digitalRecordKeeping": "true",
      |   "bridgingSoftware": "false",
      |   "viewVatReturn": "true",
      |   "viewVatLiabilities": "true",
      |   "viewVatPayments": "true",
      |   "cognitive": "false",
      |   "visual": "false",
      |   "hearing": "false",
      |   "motor": "false"
      | }
    """.stripMargin

  val json2 =
    """[
      |  {
      |    "name":"@DataDear Excel Add-in",
      |    "url":"https://www.datadear.com/hmrc-vat-return-making-tax-digital/",
      |    "features":[
      |      "businesses",
      |      "agents",
      |      "digitalRecordKeeping",
      |      "bridgingSoftware",
      |      "viewVatReturn",
      |      "viewVatLiabilities",
      |      "viewVatPayments"
      |    ]
      |  },
      |  {
      |    "name":"@gosimpletax",
      |    "url":"https://books.gosimplesoftware.co.uk/",
      |    "features":[
      |      "businesses",
      |      "agents",
      |      "digitalRecordKeeping",
      |      "viewVatReturn",
      |      "viewVatLiabilities",
      |      "viewVatPayments"
      |    ]
      |  }""".stripMargin

}
