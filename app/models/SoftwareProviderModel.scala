/*
 * Copyright 2021 HM Revenue & Customs
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

import java.net.URLEncoder

import enums.Filter
import play.api.libs.json.{JsValue, Json, Reads, Writes, __}


case class SoftwareProviderModel(name: String, url: String, filters: List[Filter.Value] = List.empty) {

  def encodedName: String = URLEncoder.encode(name, "UTF-8")

  val category: String = "^[A-Z]".r.findFirstIn(name.toUpperCase).getOrElse("#")
}

object SoftwareProviderModel {

  private def getFilter(filter: Filter.Value, hasFeature: Boolean): Option[Filter.Value] = if(hasFeature) Some(filter) else None

  implicit val reads: Reads[SoftwareProviderModel] = for {
    name <- (__ \ "name").read[String]
    url <- (__ \ "url").read[String]
    business <- (__ \ "business").read[Boolean].map{getFilter(Filter.BUSINESS, _)}
    agent <- (__ \ "agent").read[Boolean].map{getFilter(Filter.AGENT, _)}
    viewReturn <- (__ \ "viewReturn").read[Boolean].map{getFilter(Filter.VIEW_RETURN, _)}
    viewLiabilities <- (__ \ "viewLiabilities").read[Boolean].map{getFilter(Filter.VIEW_LIABILITIES, _)}
    viewPayments <- (__ \ "viewPayments").read[Boolean].map{getFilter(Filter.VIEW_PAYMENTS, _)}
    accounting <- (__ \ "accounting").read[Boolean].map{getFilter(Filter.ACCOUNTING, _)}
    spreadsheets <- (__ \ "spreadsheets").read[Boolean].map{getFilter(Filter.SPREADSHEETS, _)}
    cognitive <- (__ \ "cognitive").read[Boolean].map{getFilter(Filter.COGNITIVE, _)}
    hearing <- (__ \ "hearing").read[Boolean].map{getFilter(Filter.HEARING, _)}
    motor <- (__ \ "motor").read[Boolean].map{getFilter(Filter.MOTOR, _)}
    visual <- (__ \ "visual").read[Boolean].map{getFilter(Filter.VISUAL, _)}
    free <- (__ \ "free").readNullable[Boolean].map{ boolean => getFilter(Filter.FREE, boolean.getOrElse(false))}
    welsh <- (__ \ "welsh").readNullable[Boolean].map{ boolean => getFilter(Filter.WELSH, boolean.getOrElse(false))}
    features = List(business, agent, viewReturn, viewLiabilities, viewPayments,
      accounting, spreadsheets, cognitive, visual, hearing, motor, free, welsh).flatten
  } yield SoftwareProviderModel(name, url, features)

  implicit val writes: Writes[SoftwareProviderModel] = new Writes[SoftwareProviderModel] {
    override def writes(softwareProvider: SoftwareProviderModel): JsValue = {
      Json.obj(
        "name" -> softwareProvider.name,
        "url" -> softwareProvider.url,
        "business" -> softwareProvider.filters.contains(Filter.BUSINESS),
        "agent" -> softwareProvider.filters.contains(Filter.AGENT),
        "viewReturn" -> softwareProvider.filters.contains(Filter.VIEW_RETURN),
        "viewLiabilities" -> softwareProvider.filters.contains(Filter.VIEW_LIABILITIES),
        "viewPayments" -> softwareProvider.filters.contains(Filter.VIEW_PAYMENTS),
        "accounting" -> softwareProvider.filters.contains(Filter.ACCOUNTING),
        "spreadsheets" -> softwareProvider.filters.contains(Filter.SPREADSHEETS),
        "cognitive" -> softwareProvider.filters.contains(Filter.COGNITIVE),
        "hearing" -> softwareProvider.filters.contains(Filter.HEARING),
        "motor" -> softwareProvider.filters.contains(Filter.MOTOR),
        "visual" -> softwareProvider.filters.contains(Filter.VISUAL),
        "free" -> softwareProvider.filters.contains(Filter.FREE),
        "welsh" -> softwareProvider.filters.contains(Filter.WELSH)
      )
    }
  }
}
