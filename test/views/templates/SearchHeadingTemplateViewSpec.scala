/*
 * Copyright 2020 HM Revenue & Customs
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

package views.templates

import assets.messages.{SearchMessages, SoftwareChoicesMessages}
import forms.SearchForm
import views.ViewBaseSpec

class SearchHeadingTemplateViewSpec extends ViewBaseSpec {

  object Selectors {
    val heading = "h1"
    val searchText1 = "#wrapper > p:nth-of-type(1)"
    val searchText2 = "#wrapper > p:nth-of-type(2)"
    val searchTextLink = "p > a"
    val indentTextOne = "div.panel.panel-border-wide > p:nth-child(1)"
    val indentTextTwo = "div.panel.panel-border-wide > p:nth-child(2)"
  }

  "The Search Heading Template" should {

    lazy val document = parseView(views.html.templates.search_heading_template())

    "have the correct heading" in {
      document.select(Selectors.heading).text() shouldBe SoftwareChoicesMessages.title
    }

    "have a the correct search text with the correct link" in {
      document.select(Selectors.searchText1).text() shouldBe SearchMessages.text1
      document.select(Selectors.searchText2).text() shouldBe s"${SearchMessages.text2Start} ${SearchMessages.text2Link} ${SearchMessages.text2End}"
      document.select(Selectors.searchTextLink).attr("href") shouldBe appConfig.govUkMtdVatSignUpGuidanceUrl
    }

    "have a the correct indented text" in {
      document.select(Selectors.indentTextOne).text() shouldBe SearchMessages.p1
      document.select(Selectors.indentTextTwo).text() shouldBe SearchMessages.p2
    }
  }
}
