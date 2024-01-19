/*
 * Copyright 2024 HM Revenue & Customs
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

import testContants.SoftwareProvidersTestConstants
import models.components.{FilterFieldModel, FilterTemplateModel}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import views.ViewBaseSpec
import views.html.templates.search_filter_template

class SearchFilterTemplateViewSpec extends ViewBaseSpec with SoftwareProvidersTestConstants {

  val view = app.injector.instanceOf[search_filter_template]

  case class TestModel(foo: String, bar: String)

  val form: Form[TestModel] = Form(
    mapping(
      "foo" -> text,
      "bar" -> text
    )(TestModel.apply)(TestModel.unapply)
  )

  val filterTemplateModel1 = FilterFieldModel(form("foo"), "FilterValue1")
  val filterTemplateModel2 = FilterFieldModel(form("bar"), "FilterValue2")

  val filterTemplateModels = Seq(filterTemplateModel1, filterTemplateModel2)

  object Selectors {
    val heading = ".filter-head"
    val checkbox: Int => String = n => s"fieldset > div > div:nth-of-type($n) > input[type='checkbox']"
    val label: Int => String = n => s"fieldset > div > div:nth-of-type($n) > label"
  }

  "The Filter Template" when{

    "given a title and 3 FilterTemplateModels" should {

      lazy val document = parseView(view(FilterTemplateModel("A Title",  filterTemplateModels, true)))

      "have a title" in {
        val actualResult = document.select(Selectors.heading).text
        val expectedResult = "A Title"
        actualResult shouldBe expectedResult
      }

      "have a first option" which {

        "has the correct id" in {
          val actualResult = document.select(Selectors.checkbox(1)).attr("id")
          val expectedResult = filterTemplateModel1.field.name
          actualResult shouldBe expectedResult
        }

        "has the correct label" in {
          val actualResult = document.select(Selectors.label(1)).text
          val expectedResult = filterTemplateModel1.label
          actualResult shouldBe expectedResult
        }
      }

      "have a second option" which {

        "has the correct id" in {
          val actualResult = document.select(Selectors.checkbox(2)).attr("id")
          val expectedResult = filterTemplateModel2.field.name
          actualResult shouldBe expectedResult
        }

        "has the correct label" in {
          val actualResult = document.select(Selectors.label(2)).text
          val expectedResult = filterTemplateModel2.label
          actualResult shouldBe expectedResult
        }
      }
    }
  }
}
