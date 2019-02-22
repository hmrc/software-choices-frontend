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

package forms

import enums.Filter._
import models.FiltersFormModel
import uk.gov.hmrc.play.test.UnitSpec

class FiltersFormSpec extends UnitSpec {

  "Binding a form with invalid data" when {

    "the Search Term exceeds the max length" should {

      val maxTerm = Map(FiltersForm.term -> "a" * (FiltersForm.maxLength + 1))
      val form = FiltersForm.form.bind(maxTerm)

      "result in a form with errors" in {
        form.hasErrors shouldBe true
      }

      "throw one error" in {
        form.errors.size shouldBe 1
      }

      "have an error with the correct message" in {
        form.errors.head.message shouldBe "searchForm.term.max"
      }
    }
  }

  "Binding a form with valid data" when {

    "all filters are true" should {

      "bind successfully and create a FiltersFormModel" in {

        val actualResult = FiltersForm.form.bind(Map(
          FiltersForm.term -> "someSearchTerm",
          business -> "true",
          agent -> "true",
          viewReturn -> "true",
          viewLiabilities -> "true",
          viewPayments -> "true",
          accounting -> "true",
          spreadsheets -> "true",
          cognitive -> "true",
          hearing -> "true",
          motor -> "true",
          visual -> "true"
        )).value

        val expectedResult = Some(FiltersFormModel(
          Seq(BUSINESS, AGENT, VIEW_RETURN, VIEW_LIABILITIES, VIEW_PAYMENTS, ACCOUNTING, SPREADSHEETS, COGNITIVE, HEARING, MOTOR, VISUAL),
          Some("someSearchTerm"))
        )

        actualResult shouldBe expectedResult
      }
    }

    "some filters are true and some are false" in {

      val actualResult = FiltersForm.form.bind(Map(
        FiltersForm.term -> "someSearchTerm",
        business -> "false",
        agent -> "true",
        viewReturn -> "false",
        viewLiabilities -> "true",
        viewPayments -> "false",
        accounting -> "true",
        spreadsheets -> "false",
        cognitive -> "true",
        hearing -> "false",
        motor -> "true",
        visual -> "false"
      )).value

      val expectedResult = Some(FiltersFormModel(
        Seq(AGENT, VIEW_LIABILITIES, ACCOUNTING, COGNITIVE, MOTOR),
        Some("someSearchTerm"))
      )

      actualResult shouldBe expectedResult
    }

    "all filters are false but a search term has been provided" in {

      val actualResult = FiltersForm.form.bind(Map(
        FiltersForm.term -> "someSearchTerm",
        business -> "false",
        agent -> "false",
        viewReturn -> "false",
        viewLiabilities -> "false",
        viewPayments -> "false",
        accounting -> "false",
        spreadsheets -> "false",
        cognitive -> "false",
        hearing -> "false",
        motor -> "false",
        visual -> "false"
      )).value

      val expectedResult = Some(FiltersFormModel(
        Seq.empty,
        Some("someSearchTerm"))
      )

      actualResult shouldBe expectedResult
    }

    "all filters are false but a search term has NOT been provided" in {

      val actualResult = FiltersForm.form.bind(Map(
        business -> "false",
        agent -> "false",
        viewReturn -> "false",
        viewLiabilities -> "false",
        viewPayments -> "false",
        accounting -> "false",
        spreadsheets -> "false",
        cognitive -> "false",
        hearing -> "false",
        motor -> "false",
        visual -> "false"
      )).value

      val expectedResult = Some(FiltersFormModel(
        Seq.empty,
        None
      ))

      actualResult shouldBe expectedResult
    }
  }
}
