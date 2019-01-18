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

import models.SearchModel
import uk.gov.hmrc.play.test.UnitSpec

class SearchFormSpec extends UnitSpec {

  "Binding a form with invalid data" when {

    "the Search Term is missing" should {

      val missingTerm = Map(SearchForm.term -> "")
      val form = SearchForm.form.bind(missingTerm)

      "result in a form with errors" in {
        form.hasErrors shouldBe true
      }

      "throw one error" in {
        form.errors.size shouldBe 1
      }

      "have an error with the correct message" in {
        form.errors.head.message shouldBe "searchForm.term.missing"
      }
    }

    "the Search Term exceeds the max length" should {

      val maxTerm = Map(SearchForm.term -> "a" * (SearchForm.maxLength + 1))
      val form = SearchForm.form.bind(maxTerm)

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

  "Binding a form with valid data on the boundary" should {

    val validSearch = "a" * SearchForm.maxLength

    val data = Map(SearchForm.term -> validSearch)
    val form = SearchForm.form.bind(data)

    "result in a form with no errors" in {
      form.hasErrors shouldBe false
    }

    "generate the correct model" in {
      form.value shouldBe Some(SearchModel(validSearch))
    }
  }

  "Binding a form with valid data in the boundary" should {

    val validSearch = "a" * (SearchForm.maxLength - 1)

    val data = Map(SearchForm.term -> validSearch)
    val form = SearchForm.form.bind(data)

    "result in a form with no errors" in {
      form.hasErrors shouldBe false
    }

    "generate the correct model" in {
      form.value shouldBe Some(SearchModel(validSearch))
    }
  }

  "A form built from a valid model" should {
    "generate the correct mapping" in {
      val validSearch = "a"
      val model = SearchModel(validSearch)
      val form = SearchForm.form.fill(model)
      form.data shouldBe Map(SearchForm.term -> validSearch)
    }
  }

}
