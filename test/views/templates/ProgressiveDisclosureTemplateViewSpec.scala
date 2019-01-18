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

import models.SoftwareProviderModel
import org.jsoup.Jsoup
import play.twirl.api.Html
import utils.TestUtils

class ProgressiveDisclosureTemplateViewSpec extends TestUtils {

  object Selectors {
    val title = "span"
    val content = "p"
  }

  "The Progressive disclosure Template" when {

    "given a title and some Html content" should {

      lazy val view = views.html.templates.progressive_disclosure_template("A Title", Html("<p>Some Text</p>"))
      lazy val document = Jsoup.parse(view.body)

      "have the correct title" in {
        document.select(Selectors.title).text shouldBe "A Title"
      }

      "have the correct content" in {
        document.select(Selectors.content).text shouldBe "Some Text"
      }

    }
  }
}