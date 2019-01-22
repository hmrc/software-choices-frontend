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

package views.templates

import assets.messages
import assets.messages.SearchMessages
import forms.SearchForm
import org.jsoup.Jsoup
import utils.TestUtils

class SearchBarTemplateViewSpec extends TestUtils {

  object Selectors {
    val searchLabel = "label"
    val search = "input"
    val button = "button"
    val searchText = "form > p"
    val searchTextLink = "form > p > a"
    val indentTextOne = "div.panel.panel-border-wide > p:nth-child(1)"
    val indentTextTwo = "div.panel.panel-border-wide > p:nth-child(2)"
  }

  "The Search Bar Template" when {

    "given an empty form" should {

      lazy val view = views.html.templates.search_bar_template(SearchForm.form)
      lazy val document = Jsoup.parse(view.body)

      s"have a the correct search text with the correct link" in {
        document.select(Selectors.searchText).text() shouldBe s"${SearchMessages.text} ${SearchMessages.textLink}."
        document.select(Selectors.searchTextLink).attr("href") shouldBe "#"
      }

      s"have a the correct indented text" in {
        document.select(Selectors.indentTextOne).text() shouldBe SearchMessages.p1
        document.select(Selectors.indentTextTwo).text() shouldBe SearchMessages.p2
      }


      s"have a label for the search input which is visually hidden" in {
        document.select(Selectors.searchLabel).attr("for") shouldBe SearchForm.term
        document.select(Selectors.searchLabel).hasClass("visuallyhidden") shouldBe true
      }

      s"have a search bar with no text" in {
        document.select(Selectors.search).attr("name") shouldBe SearchForm.term
        document.select(Selectors.search).attr("value").isEmpty shouldBe true
      }

      s"have a submit button bar" in {
        document.select(Selectors.button).text shouldBe SearchMessages.buttonText
      }
    }

    "given a form with data" should {

      lazy val view = views.html.templates.search_bar_template(SearchForm.form.bind(Map(
        SearchForm.term -> "Search Term"
      )))

      lazy val document = Jsoup.parse(view.body)

      s"have a search bar" in {
        document.select(Selectors.search).attr("name") shouldBe SearchForm.term
        document.select(Selectors.search).attr("value") shouldBe "Search Term"
      }

      s"have a submit button bar" in {
        document.select(Selectors.button).text shouldBe SearchMessages.buttonText
      }
    }
  }
}
