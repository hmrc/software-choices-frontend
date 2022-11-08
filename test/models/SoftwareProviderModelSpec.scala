/*
 * Copyright 2022 HM Revenue & Customs
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

import _root_.utils.TestUtils
import enums.Filter._
import play.api.libs.json.{JsObject, Json}

class SoftwareProviderModelSpec extends TestUtils {

  val model: SoftwareProviderModel = SoftwareProviderModel(
    name = "name",
    url = "url",
    filters = List(BUSINESS, AGENT, VIEW_RETURN, VIEW_LIABILITIES, VIEW_PAYMENTS, ACCOUNTING, SPREADSHEETS, COGNITIVE, VISUAL, HEARING, MOTOR, FREE)
  )

  val json: JsObject = Json.obj(
    "name" -> "name",
    "url" -> "url",
    "business" -> true,
    "agent" -> true,
    "accounting" -> true,
    "spreadsheets" -> true,
    "viewReturn" -> true,
    "viewLiabilities" -> true,
    "viewPayments" -> true,
    "cognitive" -> true,
    "visual" -> true,
    "hearing" -> true,
    "motor" -> true,
    "free" -> true,
    "welsh" -> false
  )

  "SoftwareProviderModel" should {

    "read from Json correctly with no filters" in {

      val expectedResult = SoftwareProviderModel(
        name = "name",
        url = "url",
        filters = List.empty
      )

      val actualResult = Json.obj(
        "name" -> "name",
        "url" -> "url",
        "business" -> false,
        "agent" -> false,
        "accounting" -> false,
        "spreadsheets" -> false,
        "viewReturn" -> false,
        "viewLiabilities" -> false,
        "viewPayments" -> false,
        "cognitive" -> false,
        "visual" -> false,
        "hearing" -> false,
        "motor" -> false,
        "free" -> false
      ).as[SoftwareProviderModel]

      actualResult shouldBe expectedResult
    }

    "read from Json correctly with all filters" in {
      json.as[SoftwareProviderModel] shouldBe model
    }

    "write to Json correctly with all filters" in {
      Json.toJson(model) shouldBe json
    }
  }
}
