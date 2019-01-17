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

import models.{SoftwareChoicesViewModel, SoftwareProviderModel}
import org.jsoup.Jsoup
import utils.TestUtils

class ProviderTemplateViewSpec extends TestUtils {

  object Selectors {
    val heading = "h2"
    val providerSelector: Int => String = provider => s"li:nth-child($provider) > a"
  }

  "The Provider Template" when {

    "given multiple a category with a list of providers" should {

      val category = "A"
      val softwareProviders = Seq(
        SoftwareProviderModel("aName", "aUrl"),
        SoftwareProviderModel("anotherName", "anotherUrl"),
        SoftwareProviderModel("andAnotherName", "andAnotherUrl")
      )

      lazy val view = views.html.templates.provider_template(category, softwareProviders)
      lazy val document = Jsoup.parse(view.body)

      s"have a the correct page heading" in {
        document.select(Selectors.heading).text() shouldBe category
      }

      "for the first provider" should {

        "have the correct name" in {
          document.select(Selectors.providerSelector(1)).text() shouldBe "aName"
        }

        "have the correct link" in {
          document.select(Selectors.providerSelector(1)).attr("href") shouldBe "aUrl"
        }
      }

      "for the second provider" should {

        "have the correct name" in {
          document.select(Selectors.providerSelector(2)).text() shouldBe "anotherName"
        }

        "have the correct link" in {
          document.select(Selectors.providerSelector(2)).attr("href") shouldBe "anotherUrl"
        }
      }

      "for the third provider" should {

        "have the correct name" in {
          document.select(Selectors.providerSelector(3)).text() shouldBe "andAnotherName"
        }

        "have the correct link" in {
          document.select(Selectors.providerSelector(3)).attr("href") shouldBe "andAnotherUrl"
        }
      }
    }
  }
}
