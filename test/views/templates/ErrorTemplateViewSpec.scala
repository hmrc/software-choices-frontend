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

import views.ViewBaseSpec

class ErrorTemplateViewSpec extends ViewBaseSpec {

  object Selectors {
    val heading = "h1"
    val content = "#content p"
  }

  "The Error Template" when {

    "given a title and some Html content" should {

      lazy val document = parseView(views.html.templates.error_template("A Title", "A Heading", "Some Text"))

      "have the correct title" in {
        document.title shouldBe "A Title"
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text shouldBe "A Heading"
      }

      "have the correct content" in {
        document.select(Selectors.content).text shouldBe "Some Text"
      }
    }
  }
}
