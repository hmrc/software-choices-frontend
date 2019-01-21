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

class SoftwareChoicesResultsViewSpec extends TestUtils {

  object Selectors {
    val pageHeading = "#content h1"
    val searchText = "#content > article > p:nth-of-type(1)"
    val searchTextLink = "#content > article > p > a"
    val indentTextOne = "#content > article > div.panel.panel-border-wide > p:nth-child(1)"
    val indentTextTwo = "#content > article > div.panel.panel-border-wide > p:nth-child(2)"
    val clearSearchLink = "#content > article > form > div > a"
    val showAllLink = "#content > article details > summary > span"
    val letterHeaderSelector: Int => String = header => s"#content > article details > h2:nth-child($header)"
    val providerSelector: (Int, Int)=> String = (section, provider) =>
      s"#content > article details > ul:nth-of-type($section) > li:nth-of-type($provider) > a"
    val resultsHeading = "article > h2:nth-of-type(1)"
    val resultsPara = "#content > article > p:nth-of-type(2)"
    val resultBullet = "article ul li:nth-of-type(1) a"
  }

  "The software choices results page" when {

    "when there are results found" should {

      val softwareProviders = SoftwareChoicesViewModel(
        allProviders = Seq(
          SoftwareProviderModel("aName", "aUrl"),
          SoftwareProviderModel("bName", "bUrl")
        ),
        foundProviders = Seq(
          SoftwareProviderModel("aName", "aUrl")
        ))

      lazy val view = views.html.software_choices_results(softwareProviders, SearchForm.form)
      lazy val document = Jsoup.parse(view.body)

      s"have the correct document title" in {
        document.title shouldBe "Software that works with Making Tax Digital for VAT"
      }

      s"have the correct page heading" in {
        document.select(Selectors.pageHeading).text() shouldBe "Software that works with Making Tax Digital for VAT"
      }

      s"have a the correct search text with the correct link" in {
        document.select(Selectors.searchText).text() shouldBe "Search for software that is connected to Making Tax Digital for VAT. You must also sign up to use this service."
        document.select(Selectors.searchTextLink).attr("href") shouldBe "#"
      }

      s"have a the correct indented text" in {
        document.select(Selectors.indentTextOne).text() shouldBe "HMRC does not recommend any one software package. In case of issues with software you will need to contact your software company directly."
        document.select(Selectors.indentTextTwo).text() shouldBe "All links to software packages take you to external websites."
      }

      s"have a clear search link" in {
        document.select(Selectors.clearSearchLink).text() shouldBe "Clear search"
        document.select(Selectors.clearSearchLink).attr("href") shouldBe "#"
      }

      s"not have a show all link" in {
        document.select(Selectors.showAllLink).text().isEmpty shouldBe true
      }
    }

    "when there are NO results found" should {

      val softwareProviders = SoftwareChoicesViewModel(
        allProviders = Seq(
          SoftwareProviderModel("aName", "aUrl"),
          SoftwareProviderModel("bName", "bUrl")
        ),
        foundProviders = Seq()
      )

      lazy val view = views.html.software_choices_results(softwareProviders, SearchForm.form)
      lazy val document = Jsoup.parse(view.body)

      "have a heading for the results " in {
        document.select(Selectors.resultsHeading).text() shouldBe "No results found"
      }

      "have the correct paragraph for the results " in {
        document.select(Selectors.resultsPara).text() shouldBe "We have not found any results. See the full list of software packages or try again."
      }

      s"have a show all link" in {
        document.select(Selectors.showAllLink).text() shouldBe "Show all software providers"
      }

      "have the correct section header and a single provider for A section" in {
        document.select(Selectors.letterHeaderSelector(2)).text() shouldBe "A"
        document.select(Selectors.providerSelector(1, 1)).text() shouldBe "aName"
      }
    }
  }
}
