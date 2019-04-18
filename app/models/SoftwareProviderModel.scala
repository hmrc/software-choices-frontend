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
import play.api.libs.json.{Reads, __}


case class SoftwareProviderModel(name: String, url: String, filters: List[Filter.Value] = List.empty) {

  val category: String = "^[A-Z]".r.findFirstIn(name.toUpperCase).getOrElse("#")
}

object SoftwareProviderModel {

  def apply(fileLine: String): SoftwareProviderModel = {

    Logger.debug(s"[SoftwareChoicesService][readProviders] Provider: $fileLine")

    val fileLineArray = fileLine.split("\\|",-1)
    val filtersArray = fileLineArray.splitAt(2)._2
    val orderedFilters = Seq(BUSINESS, AGENT, ACCOUNTING, SPREADSHEETS, VIEW_RETURN, VIEW_LIABILITIES, VIEW_PAYMENTS, COGNITIVE, VISUAL, HEARING, MOTOR)

    val name = fileLineArray.head.trim
    val url = fileLineArray(1)
    val valid = Seq("X","FULLY")
    val providerFilters = orderedFilters.indices.flatMap(i => if(valid.contains(filtersArray(i).toUpperCase)) Some(orderedFilters(i)) else None).toList

    SoftwareProviderModel(name, url, providerFilters)
  }


//  implicit val reads: Reads[SoftwareProviderModel] = (
//    (__ \ "name").read[String] and
//      (__ \ "url").read[String] and
//      (__ \ "businesses").read[Boolean] and
//      (__ \ "agents").read[Boolean] and
//      (__ \ "digitalRecordKeeping").read[Boolean] and
//      (__ \ "bridgingSoftware").read[Boolean] and
//      (__ \ "viewVatReturn").read[Boolean] and
//      (__ \ "viewVatLiabilities").read[Boolean] and
//      (__ \ "viewVatPayments").read[Boolean] and
//      (__ \ "cognitive").read[Boolean] and
//      (__ \ "visual").read[Boolean] and
//      (__ \ "hearing").read[Boolean] and
//      (__ \ "motor").read[Boolean]
//  )(SoftwareProviderModel _)

  implicit val reads: Reads[SoftwareProviderModel] = for {
    name <- (__ \ "name").read[String]
    url <- (__ \ "url").read[String]
    features <- (__ \ "features").readNullable[Seq[String]].map {
      case None => Seq.empty[String]
      case Some(features) => features
    }
  } yield customApply(name, url, features)


  def customApply(name: String, url: String, features: Seq[String]): SoftwareProviderModel = {

    val orderedFilters = Seq(BUSINESS, AGENT, ACCOUNTING, SPREADSHEETS, VIEW_RETURN, VIEW_LIABILITIES, VIEW_PAYMENTS, COGNITIVE, VISUAL, HEARING, MOTOR)

    val valid = Seq("X","FULLY")
    val providerFilters = orderedFilters.indices.flatMap(i => if(valid.contains(features(i).toUpperCase)) Some(orderedFilters(i)) else None).toList

    SoftwareProviderModel(name, url, providerFilters)
  }

}
