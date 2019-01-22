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

import assets.messages.{SearchMessages, SoftwareChoicesMessages}
import assets.testContants.SoftwareProvidersTestConstants
import forms.SearchForm
import utils.ViewTestUtils

class SoftwareChoicesResultsViewSpec extends ViewTestUtils with SoftwareProvidersTestConstants {

  object Selectors {
    val pageHeading = "h1"
    val showAllLink = "article details > summary > span"
    val letterHeaderSelector: Int => String = header => s"article h2:nth-of-type($header)"
    val letterHeaderDetailsSelector: Int => String = header => s"article details h2:nth-of-type($header)"
    val providerSelector: (Int, Int)=> String = (section, provider) =>
      s"article ul:nth-of-type($section) > li:nth-of-type($provider) > a"
    val resultsHeading = "article > h2:nth-of-type(1)"
    val resultsPara = "article > p:nth-of-type(1)"
    val resultBullet = "article ul li:nth-of-type(1) a"
    val clearSearchLink = "article > div > a:nth-of-type(1)"
  }

  "The software choices results page" when {

    "when there are results found" should {

      lazy val document = parseView(views.html.software_choices_results(foundProviders, SearchForm.form))

      s"have the correct document title" in {
        document.title shouldBe SoftwareChoicesMessages.title
      }

      s"have the correct page heading" in {
        document.select(Selectors.pageHeading).text() shouldBe SoftwareChoicesMessages.title
      }

      s"have a clear search link" in {
        document.select(Selectors.clearSearchLink).text() shouldBe SearchMessages.clear
        document.select(Selectors.clearSearchLink).attr("href") shouldBe controllers.routes.SoftwareChoicesController.show().url
      }

      s"not have a show all link" in {
        document.select(Selectors.showAllLink).text().isEmpty shouldBe true
      }
    }

    "when there are NO results found" should {

      "when the progressive disclosure is enabled" should {

        lazy val document = parseView(views.html.software_choices_results(providers, SearchForm.form))

        "have a heading for the results " in {
          document.select(Selectors.resultsHeading).text() shouldBe SoftwareChoicesMessages.noResultsHeader
        }

        "have the correct paragraph for the results " in {
          document.select(Selectors.resultsPara).text() shouldBe SoftwareChoicesMessages.noResults
        }

        s"have a show all link" in {
          document.select(Selectors.showAllLink).text() shouldBe SoftwareChoicesMessages.showAll
        }

        "have single provider for A section" in {
          document.select(Selectors.providerSelector(1, 1)).text() shouldBe opensInANewTabSuffix(providers.allProviders.head.name)
        }
      }

      "when the progressive disclosure is disabled" should {

        lazy val document = parseView(views.html.software_choices_results(providers, SearchForm.form))

        "have a heading for the results " in {
          appConfig.progressiveDisclosureEnabled(false)
          document.select(Selectors.resultsHeading).text() shouldBe SoftwareChoicesMessages.noResultsHeader
        }

        "have the correct paragraph for the results " in {
          document.select(Selectors.resultsPara).text() shouldBe SoftwareChoicesMessages.noResults
        }

        s"NOT have a show all link" in {
          document.select(Selectors.showAllLink).isEmpty shouldBe true
        }

        "have a single provider for A section" in {
          document.select(Selectors.providerSelector(1, 1)).text() shouldBe opensInANewTabSuffix(providers.allProviders.head.name)
        }
      }
    }
  }
}
