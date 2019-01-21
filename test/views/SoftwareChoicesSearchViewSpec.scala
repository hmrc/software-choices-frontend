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
    val pageHeading = "#content h1"
    val showAllLink = "#content > article details > summary > span"
    val letterHeaderSelector: Int => String = header => s"#content > article details > h2:nth-child($header)"
    val providerSelector: (Int, Int)=> String = (section, provider) =>
      s"#content > article details > ul:nth-of-type($section) > li:nth-of-type($provider) > a"
  }

  "The software choices search page" when {

    "given a provider" should {

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
        document.select(Selectors.letterHeaderSelector(2)).text() shouldBe "A"
        document.select(Selectors.providerSelector(1, 1)).text() shouldBe "aName"
      }
    }
  }
}
