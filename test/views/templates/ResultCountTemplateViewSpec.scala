/*
 * Copyright 2021 HM Revenue & Customs
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
import views.html.templates.result_count_template

class ResultCountTemplateViewSpec extends ViewBaseSpec {

  var view = app.injector.instanceOf[result_count_template]

  object Selectors {
    val result = "#search-result-count"
  }

  "The Result Count Template" when {

    "There are more than 1 result found" should {

      "Return 'x results found'" in {
        val document = parseView(view(2, 10))
        document.select(Selectors.result).text shouldBe CommonMessages.results(2)
      }
    }

    "There is 1 result found" should {

      "Return '1 result found'" in {
        val document = parseView(view(1, 10))
        document.select(Selectors.result).text shouldBe CommonMessages.oneResult
      }
    }

    "There are 0 results found" should {

      "Return 'No results found'" in {
        val document = parseView(view(0, 10))
        document.select(Selectors.result).text shouldBe CommonMessages.noResults
      }
    }
  }
}
