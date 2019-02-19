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

package views

import assets.messages.{CommonMessages, FilterSearchMessages, SoftwareChoicesMessages}
import assets.testContants.SoftwareProvidersTestConstants
import forms.{FiltersForm, SearchForm}

class SoftwareChoicesFilterViewSpec extends ViewBaseSpec with SoftwareProvidersTestConstants {

  object Selectors {
    val pageHeading = "h1"
    val searchComponent = ".search-field"
    val providerTable = "#provider-table"
    val searchResultCount = "#search-result-count"
    val errorSummaryDisplay = "#error-summary-display"
    val termFieldError = "#term-error-summary"
    val formFieldError = ".form-field--error"
    val fieldErrorMessage = ".error-message"
  }

  "The software choices filter page" when {

    "the search does not contain errors" should {

      lazy val document = parseView(views.html.software_choices_filter(filterViewProviders, FiltersForm.form))

      s"have the correct document title" in {
        document.title shouldBe FilterSearchMessages.title
      }

      s"have a the correct page heading" in {
        document.select(Selectors.pageHeading).text() shouldBe FilterSearchMessages.title
      }

      "include the search component" in {
        Option(document.select(Selectors.searchComponent)).isDefined shouldBe true
      }

      "include the results count component" in {
        Option(document.select(Selectors.searchResultCount)).isDefined shouldBe true
      }

      "include the table of providers component" in {
        Option(document.select(Selectors.providerTable)).isDefined shouldBe true
      }
    }


    "the search contains errors" should {

      val errorForm = FiltersForm.form.withError("term","AN ERROR")

      lazy val document = parseView(views.html.software_choices_filter(filterViewProviders, errorForm))

      "page title should be prefixed with Error" in {
        document.title shouldBe s"${CommonMessages.error} ${FilterSearchMessages.title}"
      }

      "show the error summary" in {
        document.select(Selectors.errorSummaryDisplay).isEmpty shouldBe false
      }

      "have an error message with link to the term field" in {
        val summaryError = document.select(Selectors.termFieldError)
        summaryError.text shouldBe errorForm.errors.head.message
        summaryError.attr("href") shouldBe "#term"
        summaryError.attr("data-focuses") shouldBe "term"
      }

      "highlight the errored field" in {
        document.select(Selectors.formFieldError).isEmpty shouldBe false
        document.select(Selectors.fieldErrorMessage).text shouldBe "AN ERROR"
      }
    }
  }
}
