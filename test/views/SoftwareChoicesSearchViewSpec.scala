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

import assets.messages.{CommonMessages, SearchMessages, SoftwareChoicesMessages}
import assets.testContants.SoftwareProvidersTestConstants
import forms.SearchForm

class SoftwareChoicesSearchViewSpec extends ViewBaseSpec with SoftwareProvidersTestConstants {

  object Selectors {
    val pageHeading = "h1"
    val showAllLink = "article details > summary > span"
    val letterHeaderSelector: Int => String = header => s"article h2:nth-of-type($header)"
    val letterHeaderDetailsSelector: Int => String = header => s"article details h2:nth-of-type($header)"
    val providerSelector: (Int, Int)=> String = (section, provider) =>
      s"article ul:nth-of-type($section) > li:nth-of-type($provider) > a"
    val errorSummaryDisplay = "#error-summary-display"
    val termFieldError = "#term-error-summary"
    val formFieldError = ".form-field--error"
    val fieldErrorMessage = ".error-message"
    val clearSearchLink = "article > div > a:nth-of-type(1)"
  }

  "The software choices search page" when {

    "the progressive disclosure is enabled" should {

      lazy val document = parseView(views.html.software_choices_search(providers, SearchForm.form))

      s"have the correct document title" in {
        document.title shouldBe SoftwareChoicesMessages.title
      }

      s"have a the correct page heading" in {
        document.select(Selectors.pageHeading).text() shouldBe SoftwareChoicesMessages.title
      }

      s"have a show all link" in {
        document.select(Selectors.showAllLink).text() shouldBe SoftwareChoicesMessages.showAll
      }

      "have a single provider for A section" in {
        document.select(Selectors.providerSelector(1, 1)).text() shouldBe providers.allProviders.head.name
      }
    }

    "the progressive disclosure is disabled" should {

      lazy val document = parseView(views.html.software_choices_search(providers, SearchForm.form))

      s"NOT have a show all link" in {
        appConfig.progressiveDisclosureEnabled(false)
        document.select(Selectors.showAllLink).isEmpty shouldBe true
      }

      "have a single provider for A section" in {
        document.select(Selectors.providerSelector(1, 1)).text() shouldBe providers.allProviders.head.name
      }
    }

    "the search contains errors" should {

      val errorForm = SearchForm.form.withError("term","AN ERROR")

      lazy val document = parseView(views.html.software_choices_search(providers, errorForm))

      "page title should be prefixed with Error" in {
        document.title shouldBe s"${CommonMessages.error} ${SoftwareChoicesMessages.title}"
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
