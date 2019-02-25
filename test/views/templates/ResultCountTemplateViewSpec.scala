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

import assets.messages.CommonMessages
import views.ViewBaseSpec

class ResultCountTemplateViewSpec extends ViewBaseSpec {

  object Selectors {
    val result = "#search-result-count"
  }

  "The Result Count Template" when {

    "There are more than 0 results found" should {

      "Return 'x of n results'" in {
        val document = parseView(views.html.templates.result_count_template(1, 10))
        document.select(Selectors.result).text shouldBe CommonMessages.results(1, 10)
      }
    }

    "There are 0 results found" should {

      "Return 'No results found'" in {
        val document = parseView(views.html.templates.result_count_template(0, 10))
        document.select(Selectors.result).text shouldBe CommonMessages.noResults
      }
    }
  }
}
