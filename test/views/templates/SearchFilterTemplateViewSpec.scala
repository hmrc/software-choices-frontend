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

import assets.testContants.SoftwareProvidersTestConstants
import views.ViewBaseSpec

class SearchFilterTemplateViewSpec extends ViewBaseSpec with SoftwareProvidersTestConstants {

  object Selectors {
    val title = ".filter-head"
    val checkbox: Int => String = n => s"#location > fieldset > div:nth-child($n) > input[type='checkbox']"
    val label: Int => String = n => s"#location > fieldset > div:nth-child($n) > label"
  }

  "The Search Bar Template" when{

    "given a title and 3 FilterTemplateModels" should {

      lazy val document = parseView(views.html.templates.search_filter_template("A Title", filterTemplateModels))

      "have a title" in {
        val actualResult = document.select(Selectors.title).text
        val expectedResult = "A Title"
        actualResult shouldBe expectedResult
      }

      "have a first option" which {

        "has a value" in {
          val actualResult = document.select(Selectors.checkbox(1)).attr("value")
          val expectedResult = filterTemplateModel1.value
          actualResult shouldBe expectedResult
        }

        "has a title" in {
          val actualResult = document.select(Selectors.label(1)).text
          val expectedResult = filterTemplateModel1.title
          actualResult shouldBe expectedResult
        }
      }

      "have a second option" which {

        "has a value" in {
          val actualResult = document.select(Selectors.checkbox(2)).attr("value")
          val expectedResult = filterTemplateModel2.value
          actualResult shouldBe expectedResult
        }

        "has a title" in {
          val actualResult = document.select(Selectors.label(2)).text
          val expectedResult = filterTemplateModel2.title
          actualResult shouldBe expectedResult
        }
      }

      "have a third option" which {

        "has a value" in {
          val actualResult = document.select(Selectors.checkbox(3)).attr("value")
          val expectedResult = filterTemplateModel3.value
          actualResult shouldBe expectedResult
        }

        "has a title" in {
          val actualResult = document.select(Selectors.label(3)).text
          val expectedResult = filterTemplateModel3.title
          actualResult shouldBe expectedResult
        }
      }
    }
  }
}
