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

import forms.SearchForm
import models.{SoftwareChoicesViewModel, SoftwareProviderModel}
import org.jsoup.Jsoup
import utils.TestUtils

class SoftwareChoicesSearchViewSpec extends TestUtils {

  object Selectors {
    val pageHeading = "h1"
    val showAllLink = "article details > summary > span"
    val letterHeaderSelector: Int => String = header => s"article h2:nth-of-type($header)"
    val letterHeaderDetailsSelector: Int => String = header => s"article details h2:nth-of-type($header)"
    val providerSelector: (Int, Int)=> String = (section, provider) =>
      s"article ul:nth-of-type($section) > li:nth-of-type($provider) > a"
    val errorSummaryDisplay = "#error-summary-display"
    val termFieldError = "#term-error-summary"
  }

  "The software choices search page" when {

    "the progressive disclosure is enabled" should {

      val softwareProviders = SoftwareChoicesViewModel(Seq(
        SoftwareProviderModel("aName", "aUrl")
      ))

      lazy val view = views.html.software_choices_search(softwareProviders, SearchForm.form)
      lazy val document = Jsoup.parse(view.body)

      s"have the correct document title" in {
        document.title shouldBe "Software that works with Making Tax Digital for VAT"
      }

      s"have a the correct page heading" in {
        document.select(Selectors.pageHeading).text() shouldBe "Software that works with Making Tax Digital for VAT"
      }

      s"have a show all link" in {
        document.select(Selectors.showAllLink).text() shouldBe "Show all software providers"
      }

      "have the correct section header and a single provider for A section" in {
        document.select(Selectors.letterHeaderDetailsSelector(1)).text() shouldBe "A"
        document.select(Selectors.providerSelector(1, 1)).text() shouldBe "aName"
      }
    }

    "the progressive disclosure is disabled" should {

      val softwareProviders = SoftwareChoicesViewModel(Seq(
        SoftwareProviderModel("aName", "aUrl")
      ))

      lazy val view = views.html.software_choices_search(softwareProviders, SearchForm.form)
      lazy val document = Jsoup.parse(view.body)

      s"NOT have a show all link" in {
        appConfig.progressiveDisclosureEnabled(false)
        document.select(Selectors.showAllLink).isEmpty shouldBe true
      }

      "have the correct section header and a single provider for A section" in {
        document.select(Selectors.letterHeaderSelector(1)).text() shouldBe "A"
        document.select(Selectors.providerSelector(1, 1)).text() shouldBe "aName"
      }
    }

    "the search contains errors" should {

      val softwareProviders = SoftwareChoicesViewModel(Seq(
        SoftwareProviderModel("aName", "aUrl")
      ))

      val errorForm = SearchForm.form.withError("term","AN ERROR")

      lazy val view = views.html.software_choices_search(softwareProviders, errorForm)
      lazy val document = Jsoup.parse(view.body)

      "page title should be prefixed with Error" in {
        document.title shouldBe "Error: Software that works with Making Tax Digital for VAT"
      }

      "show the error summary" in {
        document.select(Selectors.errorSummaryDisplay).isEmpty shouldBe false
      }

      "have an error message with link to the term field" in {
        document.select(Selectors.termFieldError).text shouldBe errorForm.errors.head.message
        document.select(Selectors.termFieldError).attr("href") shouldBe "#term"
        document.select(Selectors.termFieldError).attr("data-focuses") shouldBe "term"
      }
    }
  }
}
