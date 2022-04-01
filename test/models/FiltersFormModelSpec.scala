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

class FiltersFormModelSpec extends TestUtils {

  "FiltersFormModel" should {
    "contain a list of filters" in {

      val actualResult = FiltersFormModel(Seq(AGENT, BUSINESS)).filters
      val expectedResult = Seq(AGENT, BUSINESS)

      actualResult shouldBe expectedResult
    }

    "contain a search term when defined" in {

      val actualResult = FiltersFormModel(Seq.empty, Some("Abratax")).searchTerm
      val expectedResult = Some("Abratax")

      actualResult shouldBe expectedResult
    }
  }

  "FiltersFormModel.customApply" when {

    "all filters are applied" should {

      "create a model with a list of all filters" in {

        val actualResult = FiltersFormModel.customApply(
          Some("term"),
          Some(BUSINESS),
          Some(AGENT),
          Some(VIEW_RETURN),
          Some(VIEW_LIABILITIES),
          Some(VIEW_PAYMENTS),
          Some(ACCOUNTING),
          Some(SPREADSHEETS),
          Some(COGNITIVE),
          Some(HEARING),
          Some(MOTOR),
          Some(VISUAL),
          Some(FREE),
          Some(WELSH)
        ).filters

        val expectedResult =
          Seq(BUSINESS, AGENT, VIEW_RETURN, VIEW_LIABILITIES, VIEW_PAYMENTS, ACCOUNTING, SPREADSHEETS,COGNITIVE, HEARING, MOTOR, VISUAL, FREE, WELSH)

        actualResult shouldBe expectedResult

      }
    }

    "Agent, View Return and Accounting filters are applied" should {

      "create a model with Agent, View Return and Accounting" in {

        val actualResult = FiltersFormModel.customApply(
          agent = Some(AGENT),
          viewReturn = Some(VIEW_RETURN),
          accounting = Some(ACCOUNTING)
        ).filters

        val expectedResult = Seq(AGENT, VIEW_RETURN, ACCOUNTING)

        actualResult shouldBe expectedResult

      }
    }


    "No filters are applied" should {

      "create a model with Agent, View Return and Accounting" in {
        FiltersFormModel.customApply().filters shouldBe Seq()
      }
    }
  }

  "FiltersFormModel.customUnapply" when {

    "all filters are applied" should {

      "return all values" in {

        val actualResult = FiltersFormModel.customUnapply(FiltersFormModel(
          Seq(BUSINESS, AGENT, VIEW_RETURN, VIEW_LIABILITIES, VIEW_PAYMENTS, ACCOUNTING, SPREADSHEETS,COGNITIVE, HEARING, MOTOR, VISUAL, FREE, WELSH),
          Some("term")
        ))

        val expectedResult = Some((
          Some("term"),
          Some(BUSINESS),
          Some(AGENT),
          Some(VIEW_RETURN),
          Some(VIEW_LIABILITIES),
          Some(VIEW_PAYMENTS),
          Some(ACCOUNTING),
          Some(SPREADSHEETS),
          Some(COGNITIVE),
          Some(HEARING),
          Some(MOTOR),
          Some(VISUAL),
          Some(FREE),
          Some(WELSH)
        ))

        actualResult shouldBe expectedResult

      }
    }

    "Agent, View Return and Accounting filters are applied" should {

      "return Agent, View Return and Accounting values" in {

        val actualResult = FiltersFormModel.customUnapply(FiltersFormModel(
          Seq(AGENT, VIEW_RETURN, ACCOUNTING)
        ))

        val expectedResult = Some((None, None, Some(AGENT), Some(VIEW_RETURN), None, None, Some(ACCOUNTING), None, None, None, None, None, None, None))

        actualResult shouldBe expectedResult

      }
    }


    "No filters are applied" should {

      "return no values" in {
        FiltersFormModel.customUnapply(FiltersFormModel(Seq())) shouldBe
          Some((None, None, None, None, None, None, None, None, None, None, None, None, None, None))
      }
    }
  }

}
